package com.nixstudio.moviemax.viewmodels

import androidx.lifecycle.ViewModel
import com.nixstudio.moviemax.domain.model.Movie
import com.nixstudio.moviemax.domain.usecase.MovieMaxUseCase
import kotlinx.coroutines.flow.Flow

class MovieViewModel(private val useCase: MovieMaxUseCase) : ViewModel() {
    fun getMovies(): Flow<List<Movie>> = useCase.getDiscoveryMovie()
}