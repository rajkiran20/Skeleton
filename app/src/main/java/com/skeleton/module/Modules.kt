package com.skeleton.module

import `in`.org.projecteka.featuresprovider.BuildConfig
import com.skeleton.network.createNetworkClient
import com.skeleton.search.remote.LandingApis
import com.skeleton.search.repository.LandingRepository
import com.skeleton.search.repository.LandingRepositoryImpl
import com.skeleton.search.viewmodel.LandingViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { LandingViewModel(get()) }
}

val repositoryModule = module {
    factory { LandingRepositoryImpl(get()) as LandingRepository }
}

val networkModule = module {
    single { createNetworkClient(get(), BuildConfig.DEBUG).create(LandingApis::class.java) }
}
