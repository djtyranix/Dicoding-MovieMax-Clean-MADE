package com.nixstudio.moviemax.favorite.viewmodel

import androidx.lifecycle.ViewModel
import com.nixstudio.moviemax.core.data.entities.utils.MediaType
import com.nixstudio.moviemax.core.domain.usecase.MovieMaxUseCase

class FavoriteViewModel(private val useCase: MovieMaxUseCase) : ViewModel() {

    fun getFavorites() = useCase.getAllFavorites()

    fun getFavoritesByMediaType(mediaType: MediaType) = useCase.getFavoritesFromMediaType(mediaType)
}