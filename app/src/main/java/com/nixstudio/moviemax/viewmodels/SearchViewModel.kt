package com.nixstudio.moviemax.viewmodels

import androidx.lifecycle.ViewModel
import com.nixstudio.moviemax.domain.model.Combined
import com.nixstudio.moviemax.domain.usecase.MovieMaxUseCase
import kotlinx.coroutines.flow.Flow

class SearchViewModel(private val useCase: MovieMaxUseCase) : ViewModel() {
    fun getSearchResults(string: String): Flow<List<Combined>> =
        useCase.searchByString(string)
}