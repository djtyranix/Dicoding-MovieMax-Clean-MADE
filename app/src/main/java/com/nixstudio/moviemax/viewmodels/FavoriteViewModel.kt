package com.nixstudio.moviemax.viewmodels

import androidx.lifecycle.ViewModel
import com.nixstudio.moviemax.data.utils.MediaType
import com.nixstudio.moviemax.domain.usecase.MovieMaxUseCase
import kotlinx.coroutines.flow.Flow

class FavoriteViewModel(private val useCase: MovieMaxUseCase) : ViewModel() {

    fun getFavorites() = useCase.getAllFavorites()

    fun getFavoritesByMediaType(mediaType: MediaType) = useCase.getFavoritesFromMediaType(mediaType)

    fun getItemCount(): Flow<Int> = useCase.getDbItemCount()
}