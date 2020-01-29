package com.skeleton.search.repository

import com.skeleton.core.model.Brand
import com.skeleton.search.remote.LandingApis
import retrofit2.Call

interface LandingRepository {
    fun getPopularBrands(): Call<List<Brand>>
}

class LandingRepositoryImpl(private val landingApi: LandingApis) : LandingRepository {
    override fun getPopularBrands(): Call<List<Brand>> {
        return landingApi.getPopularBrands()
    }
}
