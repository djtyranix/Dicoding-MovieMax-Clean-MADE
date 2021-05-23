package com.nixstudio.moviemax.utils.modules

import com.nixstudio.moviemax.domain.usecase.MovieMaxInteractor
import com.nixstudio.moviemax.domain.usecase.MovieMaxUseCase
import org.koin.dsl.module

val useCaseModule = module {
    factory<MovieMaxUseCase> {
        MovieMaxInteractor(get())
    }
}