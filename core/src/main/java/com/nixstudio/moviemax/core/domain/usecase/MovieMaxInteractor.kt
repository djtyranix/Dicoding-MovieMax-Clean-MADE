package com.nixstudio.moviemax.core.domain.usecase

import androidx.paging.PagingData
import com.nixstudio.moviemax.core.data.entities.utils.MediaType
import com.nixstudio.moviemax.core.domain.model.Combined
import com.nixstudio.moviemax.core.domain.model.Favorite
import com.nixstudio.moviemax.core.domain.model.Movie
import com.nixstudio.moviemax.core.domain.model.TvShow
import com.nixstudio.moviemax.core.domain.repository.MovieMaxRepositoryInterface
import kotlinx.coroutines.flow.Flow

class MovieMaxInteractor(private val repository: MovieMaxRepositoryInterface) : MovieMaxUseCase {
    override fun getDiscoveryMovie(): Flow<List<Movie>> = repository.getDiscoveryMovie()

    override fun getDiscoveryTvShows(): Flow<List<TvShow>> = repository.getDiscoveryTvShows()

    override fun searchByString(query: String): Flow<List<Combined>> =
        repository.searchByString(query)

    override fun getTrendingToday(): Flow<List<Combined>> = repository.getTrendingToday()

    override fun getMovieById(id: Long): Flow<Movie> = repository.getMovieById(id)

    override fun getTvShowsById(id: Long): Flow<TvShow> = repository.getTvShowsById(id)

    override fun getAllFavorites(): Flow<PagingData<Favorite>> = repository.getAllFavorites()

    override fun getFavoritesFromMediaType(mediaType: MediaType): Flow<PagingData<Favorite>> =
        repository.getFavoritesFromMediaType(mediaType)

    override fun addFavorite(movie: Movie?, tvShow: TvShow?) = repository.addFavorite(movie, tvShow)

    override fun removeFavorite(movie: Movie?, tvShow: TvShow?) =
        repository.removeFavorite(movie, tvShow)

    override fun checkIfFavoriteExist(id: Long): Int = repository.checkIfFavoriteExist(id)
}