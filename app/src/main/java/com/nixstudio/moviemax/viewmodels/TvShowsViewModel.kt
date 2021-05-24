package com.nixstudio.moviemax.viewmodels

import androidx.lifecycle.ViewModel
import com.nixstudio.moviemax.core.domain.model.TvShow
import com.nixstudio.moviemax.core.domain.usecase.MovieMaxUseCase
import kotlinx.coroutines.flow.Flow


class TvShowsViewModel(private val useCase: MovieMaxUseCase) : ViewModel() {
    fun getTvShows(): Flow<List<TvShow>> = useCase.getDiscoveryTvShows()
}