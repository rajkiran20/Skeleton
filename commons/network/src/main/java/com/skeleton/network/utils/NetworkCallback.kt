package com.skeleton.network.utils

import okhttp3.ResponseBody
import retrofit2.Response

interface NetworkCallback {
    fun <T> onSuccess(body: T)
    fun onFailure(errorBody: ResponseBody)
    fun onFailure(t: Throwable)
}