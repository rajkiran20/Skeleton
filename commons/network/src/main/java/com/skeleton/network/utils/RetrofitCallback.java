package com.skeleton.network.utils;

import androidx.lifecycle.MutableLiveData;

import com.skeleton.network.utils.NetworkCallback;

import org.jetbrains.annotations.NotNull;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public abstract class RetrofitCallback<T> implements Callback<T> {

    private NetworkCallback networkCallback;

    public RetrofitCallback(NetworkCallback networkCallback) {
        this.networkCallback = networkCallback;
    }

    abstract protected MutableLiveData<T> observableLiveData();

    @Override
    public void onResponse(@NotNull Call<T> call, Response<T> response) {
        if (response.isSuccessful()) {
            observableLiveData().setValue(response.body());
            networkCallback.onSuccess(response.body());
        } else {
            networkCallback.onFailure(response.errorBody());
        }
    }

    @Override
    public void onFailure(@NotNull Call<T> call, @NotNull Throwable t) {
        networkCallback.onFailure(t);
    }
}
