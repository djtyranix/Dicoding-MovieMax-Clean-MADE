@file:Suppress("unused")

package com.nixstudio.moviemax

import android.app.Application
import com.nixstudio.moviemax.core.modules.*
import com.nixstudio.moviemax.modules.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class App : Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@App)
            modules(
                listOf(
                    localDataSourceModule,
                    remoteDataSourceModule,
                    repositoryModule,
                    useCaseModule,
                    viewModelModule,
                    retrofitModule,
                    apiModule,
                    databaseModule
                )
            )
        }
    }
}