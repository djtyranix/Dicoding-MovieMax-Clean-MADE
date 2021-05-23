package com.nixstudio.moviemax.domain.usecase

import androidx.paging.PagingData
import com.nixstudio.moviemax.data.utils.MediaType
import com.nixstudio.moviemax.domain.model.Combined
import com.nixstudio.moviemax.domain.model.Favorite
import com.nixstudio.moviemax.domain.model.Movie
import com.nixstudio.moviemax.domain.model.TvShow
import kotlinx.coroutines.flow.Flow

interface MovieMaxUseCase {
    fun getDiscoveryMovie(): Flow<List<Movie>>

    fun getDiscoveryTvShows(): Flow<List<TvShow>>

    fun searchByString(query: String): Flow<List<Combined>>

    fun getTrendingToday(): Flow<List<Combined>>

    fun getMovieById(id: Long): Flow<Movie>

    fun getTvShowsById(id: Long): Flow<TvShow>

    fun getAllFavorites(): Flow<PagingData<Favorite>>

    fun getFavoritesFromMediaType(mediaType: MediaType): Flow<PagingData<Favorite>>

    fun addFavorite(movie: Movie? = null, tvShow: TvShow? = null)

    fun removeFavorite(movie: Movie? = null, tvShow: TvShow? = null)

    fun checkIfFavoriteExist(id: Long): Int

    fun getDbItemCount(): Flow<Int>
}