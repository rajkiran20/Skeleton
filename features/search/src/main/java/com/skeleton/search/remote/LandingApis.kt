package com.skeleton.search.remote

import com.skeleton.core.model.Brand
import retrofit2.Call
import retrofit2.http.GET

interface LandingApis {
    @GET("popular-brands")
    fun getPopularBrands(): Call<List<Brand>>
}
