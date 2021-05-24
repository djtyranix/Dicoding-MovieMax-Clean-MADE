package com.nixstudio.moviemax.core.modules

import com.nixstudio.moviemax.core.domain.usecase.MovieMaxInteractor
import com.nixstudio.moviemax.core.domain.usecase.MovieMaxUseCase
import org.koin.dsl.module

val useCaseModule = module {
    factory<MovieMaxUseCase> {
        MovieMaxInteractor(get())
    }
}