package com.nixstudio.moviemax.viewmodels

import androidx.lifecycle.ViewModel
import com.nixstudio.moviemax.core.domain.model.Combined
import com.nixstudio.moviemax.core.domain.usecase.MovieMaxUseCase
import kotlinx.coroutines.flow.Flow

class SearchViewModel(private val useCase: MovieMaxUseCase) : ViewModel() {
    fun getSearchResults(string: String): Flow<List<Combined>> =
        useCase.searchByString(string)
}