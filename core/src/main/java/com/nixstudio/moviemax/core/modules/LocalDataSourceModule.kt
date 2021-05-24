package com.nixstudio.moviemax.core.modules

import com.nixstudio.moviemax.core.data.sources.local.LocalDataSource
import org.koin.dsl.module

val localDataSourceModule = module {
    single {
        LocalDataSource(get())
    }
}