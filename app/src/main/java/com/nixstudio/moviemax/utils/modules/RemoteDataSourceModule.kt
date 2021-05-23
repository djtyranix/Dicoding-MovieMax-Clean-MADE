package com.nixstudio.moviemax.utils.modules

import com.nixstudio.moviemax.data.sources.remote.RemoteDataSource
import org.koin.dsl.module

val remoteDataSourceModule = module {
    single {
        RemoteDataSource(get())
    }
}