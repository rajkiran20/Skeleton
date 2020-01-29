package com.skeleton

import com.skeleton.module.networkModule
import com.skeleton.module.repositoryModule
import com.skeleton.module.viewModelModule
import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.loadKoinModules
import org.koin.core.context.startKoin
import timber.log.Timber

open class SkeletonApp : Application() {
    override fun onCreate() {
        super.onCreate()
        Timber.plant(object : Timber.DebugTree() {
            override fun createStackElementTag(element: StackTraceElement): String {
                return super.createStackElementTag(element) + ":" + element.lineNumber
            }
        })
        startKoin {
            androidLogger()
            androidContext(this@SkeletonApp)
            loadKoinModules(listOf(viewModelModule, repositoryModule, networkModule))
        }
    }
}
