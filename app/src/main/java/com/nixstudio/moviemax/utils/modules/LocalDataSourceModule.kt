package com.nixstudio.moviemax.utils.modules

import com.nixstudio.moviemax.data.sources.local.LocalDataSource
import org.koin.dsl.module

val localDataSourceModule = module {
    single {
        LocalDataSource(get())
    }
}