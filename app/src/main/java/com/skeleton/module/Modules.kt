package com.skeleton.module

import com.skeleton.BuildConfig
import com.skeleton.network.createNetworkClient
import com.skeleton.remote.LandingApis
import com.skeleton.repository.LandingRepository
import com.skeleton.repository.LandingRepositoryImpl
import com.skeleton.viewmodel.LandingViewModel
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
