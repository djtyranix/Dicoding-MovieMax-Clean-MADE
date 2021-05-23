package com.nixstudio.moviemax.utils.modules

import com.nixstudio.moviemax.domain.repository.MovieMaxRepository
import com.nixstudio.moviemax.domain.repository.MovieMaxRepositoryInterface
import org.koin.dsl.module

val repositoryModule = module {
    single<MovieMaxRepositoryInterface> {
        MovieMaxRepository(get(), get())
    }
}