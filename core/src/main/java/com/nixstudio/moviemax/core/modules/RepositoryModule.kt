package com.nixstudio.moviemax.core.modules

import com.nixstudio.moviemax.core.domain.repository.MovieMaxRepositoryInterface
import org.koin.dsl.module

val repositoryModule = module {
    single<MovieMaxRepositoryInterface> {
        com.nixstudio.moviemax.core.data.MovieMaxRepository(get(), get())
    }
}