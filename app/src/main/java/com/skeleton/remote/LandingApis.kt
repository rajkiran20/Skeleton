package com.skeleton.remote

import io.reactivex.rxjava3.core.Single
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface LandingApis {
    @POST("api/v1/landing")
    fun getLandingResponse(@Body someBody: String): Single<String>

    @POST("api/v1/landing")
    fun getLandingResponse2(someBody: String): Call<String>
}