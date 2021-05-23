package com.nixstudio.moviemax.viewmodels

import androidx.lifecycle.ViewModel
import com.nixstudio.moviemax.domain.model.Combined
import com.nixstudio.moviemax.domain.model.Movie
import com.nixstudio.moviemax.domain.model.TvShow
import com.nixstudio.moviemax.domain.usecase.MovieMaxUseCase
import kotlinx.coroutines.flow.Flow

class HomeViewModel(private val useCase: MovieMaxUseCase) : ViewModel() {
    fun getTvShows(): Flow<List<TvShow>> = useCase.getDiscoveryTvShows()

    fun getMovies(): Flow<List<Movie>> = useCase.getDiscoveryMovie()

    fun getTrending(): Flow<List<Combined>> = useCase.getTrendingToday()
}