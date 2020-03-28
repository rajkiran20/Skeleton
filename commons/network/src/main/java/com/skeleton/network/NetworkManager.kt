package com.skeleton.network

import com.skeleton.util.constant.NetworkConstants.Companion.MOCK_WEB_SERVER_TEST_URL
import android.app.Application
import android.content.Context
import com.skeleton.network.interceptor.HostSelectionInterceptor
import com.skeleton.network.interceptor.RequestInterceptor
import com.skeleton.util.constant.NetworkConstants.Companion.CONNECT_TIMEOUT
import com.skeleton.util.constant.NetworkConstants.Companion.READ_TIMEOUT
import com.skeleton.util.constant.NetworkConstants.Companion.WRITE_TIMEOUT
import com.skeleton.util.sharedPref.getAuthToken
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

fun createNetworkClient(context: Application, debug: Boolean = false) =
    retrofitClient(getBaseUrl(context), httpClient(debug, context, context.getAuthToken()))

fun getBaseUrl(context: Application): String {
    return if (isTestingMode(context)) MOCK_WEB_SERVER_TEST_URL else getBaseUrl(context)
}

private fun httpClient(debug: Boolean, context: Context, authToken: String?): OkHttpClient {
    val httpLoggingInterceptor = HttpLoggingInterceptor(HttpLoggingInterceptor.Logger.DEFAULT)
    val clientBuilder = OkHttpClient.Builder()
    clientBuilder.connectTimeout(CONNECT_TIMEOUT, TimeUnit.SECONDS)
        .readTimeout(READ_TIMEOUT, TimeUnit.SECONDS)
        .writeTimeout(WRITE_TIMEOUT, TimeUnit.SECONDS)

    clientBuilder.addInterceptor(RequestInterceptor(authToken))

    if (debug) {
        addRequestResponseLogger(httpLoggingInterceptor, clientBuilder)
        addBaseUrlChanger(clientBuilder, context)
    }

    return clientBuilder.build()
}

private fun addBaseUrlChanger(clientBuilder: OkHttpClient.Builder, context: Context) {
    clientBuilder.addInterceptor(HostSelectionInterceptor(context))
}

private fun addRequestResponseLogger(
    httpLoggingInterceptor: HttpLoggingInterceptor,
    clientBuilder: OkHttpClient.Builder
) {
    httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
    clientBuilder.addInterceptor(httpLoggingInterceptor)
}

private fun retrofitClient(baseUrl: String, httpClient: OkHttpClient): Retrofit {
    return Retrofit.Builder()
        .baseUrl(baseUrl)
        .client(httpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .build()
}

private fun isTestingMode(context: Application) = context.javaClass.simpleName != "JataayuApp"
