package com.skeleton.network.utils

import androidx.lifecycle.MutableLiveData
import com.skeleton.network.utils.NetworkCallback
import com.skeleton.network.utils.RetrofitCallback
import retrofit2.Call

fun <T> Call<T>.observeOn(offers: MutableLiveData<T>, networkCallback: NetworkCallback) {
    enqueue(object: RetrofitCallback<T>(networkCallback) {
        override fun observableLiveData(): MutableLiveData<T> {
            return offers
        }
    })
}