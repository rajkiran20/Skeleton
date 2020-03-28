package com.skeleton.viewmodel

import android.content.res.Resources
import androidx.lifecycle.ViewModel
import com.skeleton.network.utils.NetworkCallback
import com.skeleton.util.extension.liveDataOf
import com.skeleton.network.utils.observeOn
import com.skeleton.presentation.callback.ProgressLoaderView
import com.skeleton.repository.LandingRepository
import com.skeleton.util.extension.liveDataOf
import io.reactivex.rxjava3.disposables.CompositeDisposable

class LandingViewModel(private val landingRepository: LandingRepository) : ViewModel() {
    val landingResponse = liveDataOf<String>()
    private val compositeDisposable: CompositeDisposable by lazy { CompositeDisposable() }

    fun getString(
        progressLoaderView: ProgressLoaderView,
        resources: Resources
    ) {
        compositeDisposable.add(landingRepository.getLandingResponse("someText")
            .doOnSubscribe { progressLoaderView.showLoader() }
            .doOnEvent { _, _ -> progressLoaderView.hideLoader() }
            .doOnSuccess {
                landingResponse.postValue(it)
            }
            .subscribe())
    }

    fun getOffersForCategory(someString: String, networkCallback: NetworkCallback) {
        landingRepository.getLandingResponse2(someString)
            .observeOn(landingResponse, networkCallback)
    }

    override fun onCleared() {
        compositeDisposable.clear()
        super.onCleared()
    }
}
