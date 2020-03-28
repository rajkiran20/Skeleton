package com.skeleton.network.utils

import android.util.Log
import io.reactivex.rxjava3.core.BackpressureStrategy
import io.reactivex.rxjava3.core.Flowable
import io.reactivex.rxjava3.core.Scheduler
import io.reactivex.rxjava3.core.SingleTransformer
import io.reactivex.rxjava3.schedulers.Schedulers
import okhttp3.Request
import okhttp3.Response
import okio.Buffer
import retrofit2.HttpException
import java.io.IOException
import java.net.SocketTimeoutException
import java.nio.charset.Charset
import java.util.concurrent.TimeUnit.MILLISECONDS

private const val MAX_RETRY_COUNT = 3L
private const val RETRY_DELAY_IN_MILLIS = 2000L

interface RxSingleRetry<T> {
    fun get(): SingleTransformer<T, T>

    companion object Strategy {

        fun <T> linearRetryOnError(
            maxAttempts: Long = MAX_RETRY_COUNT,
            delayInMs: Long = RETRY_DELAY_IN_MILLIS,
            scheduler: Scheduler = Schedulers.computation(),
            predicate: (t: Throwable) -> Boolean
        ): SingleTransformer<T, T> {
            return SingleTransformer {
                var retryCount = 0L

                it.retryWhen {
                    it.flatMap { exception ->
                        if (predicate(exception) && retryCount < maxAttempts) {
                            Flowable.create<Boolean>(
                                    { it.onNext(true) },
                                    BackpressureStrategy.LATEST
                            ).delay(
                                    delayInMs,
                                    MILLISECONDS,
                                    scheduler
                            )
                        } else {
                            Flowable.error<Boolean>(exception)
                        }
                    }.doOnNext { retryCount++ }
                }
            }
        }

        fun <T> linearRetryOnNetworkError(
            maxAttempts: Long = MAX_RETRY_COUNT,
            retryDelayInMillis: Long = RETRY_DELAY_IN_MILLIS
        ): SingleTransformer<T, T> {
            return linearRetryOnError(maxAttempts, retryDelayInMillis) {
                it.isNetworkException() || it.isHttpException()
            }
        }

        fun <T> noRetry(): SingleTransformer<T, T> {
            return linearRetryOnError(
                    maxAttempts = 0,
                    delayInMs = 0
            ) {
                false
            }
        }
    }
}

fun Throwable.isHttpException(): Boolean = this is HttpException

fun Throwable.isNetworkException(): Boolean =
    this is SocketTimeoutException || this is IOException

fun Response.parseBody(): String {
    return this.body?.let {
        try {
            val source = it.source()
            source.request(Long.MAX_VALUE)
            source.buffer().clone().readString(Charset.forName("UTF-8"))
        } catch (e: IOException) {
            Log.e("NetworkUtils", e.message, e)
            String.empty()
        }
    } ?: String.empty()
}

fun Request.parseBody(): String {
    return body?.let {
        try {
            val requestCopy = newBuilder().build()
            val buffer = Buffer()
            requestCopy.body!!.writeTo(buffer)
            buffer.readUtf8()
        } catch (e: IOException) {
            Log.e("NetworkUtils", e.message, e)
            String.empty()
        }
    } ?: String.empty()
}

private fun String.Companion.empty(): String = ""
