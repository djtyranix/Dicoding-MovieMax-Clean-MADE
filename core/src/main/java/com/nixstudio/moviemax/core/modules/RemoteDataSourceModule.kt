package com.nixstudio.moviemax.core.modules

import com.nixstudio.moviemax.core.data.sources.remote.RemoteDataSource
import org.koin.dsl.module

val remoteDataSourceModule = module {
    single {
        RemoteDataSource(get())
    }
}