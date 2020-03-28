package com.skeleton.repository

import com.skeleton.network.utils.RxSingleRetry
import com.skeleton.remote.LandingApis
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers
import retrofit2.Call

interface LandingRepository {
    fun getLandingResponse(someBody: String): Single<String>
    fun getLandingResponse2(someBody: String): Call<String>
}

class LandingRepositoryImpl(private val landingApis: LandingApis) :
    LandingRepository {
    override fun getLandingResponse(someBody: String): Single<String> {
        return landingApis.getLandingResponse(someBody)
            .compose(RxSingleRetry.linearRetryOnNetworkError())
            .subscribeOn(Schedulers.io())
            .map { it }
    }

    override fun getLandingResponse2(someBody: String): Call<String> {
        return landingApis.getLandingResponse2(someBody)
    }
}