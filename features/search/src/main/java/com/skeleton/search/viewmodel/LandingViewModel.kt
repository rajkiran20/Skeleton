package com.skeleton.search.viewmodel

import com.skeleton.core.model.Brand
import com.skeleton.search.repository.LandingRepository
import com.skeleton.util.extension.liveDataOf
import androidx.lifecycle.ViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import timber.log.Timber

class LandingViewModel(val landingRepository: LandingRepository) : ViewModel() {
    val brands = liveDataOf<List<Brand>>()

    fun getPopularBrands() {
        landingRepository.getPopularBrands().enqueue(object : Callback<List<Brand>?> {
            override fun onFailure(call: Call<List<Brand>?>, t: Throwable) {
                Timber.e(t, "Failed to get brands list")
            }
            override fun onResponse(call: Call<List<Brand>?>, response: Response<List<Brand>?>) {
                if (response.isSuccessful) {
                    response.body()?.let {
                        brands.value = response.body()
                    }
                }
            }
        })
    }
}

