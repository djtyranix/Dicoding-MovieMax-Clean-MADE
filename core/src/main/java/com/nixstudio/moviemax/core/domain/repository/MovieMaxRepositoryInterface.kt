package com.nixstudio.moviemax.core.domain.repository

import androidx.paging.PagingData
import com.nixstudio.moviemax.core.data.entities.utils.MediaType
import com.nixstudio.moviemax.core.domain.model.Combined
import com.nixstudio.moviemax.core.domain.model.Favorite
import com.nixstudio.moviemax.core.domain.model.Movie
import com.nixstudio.moviemax.core.domain.model.TvShow
import kotlinx.coroutines.flow.Flow

interface MovieMaxRepositoryInterface {
    fun getDiscoveryMovie(page: Int = 1): Flow<List<Movie>>

    fun getDiscoveryTvShows(page: Int = 1): Flow<List<TvShow>>

    fun searchByString(query: String, page: Int = 1): Flow<List<Combined>>

    fun getTrendingToday(): Flow<List<Combined>>

    fun getMovieById(id: Long): Flow<Movie>

    fun getTvShowsById(id: Long): Flow<TvShow>

    fun getAllFavorites(): Flow<PagingData<Favorite>>

    fun getFavoritesFromMediaType(mediaType: MediaType): Flow<PagingData<Favorite>>

    fun addFavorite(movie: Movie? = null, tvShow: TvShow? = null)

    fun removeFavorite(movie: Movie? = null, tvShow: TvShow? = null)

    fun checkIfFavoriteExist(id: Long): Int
}