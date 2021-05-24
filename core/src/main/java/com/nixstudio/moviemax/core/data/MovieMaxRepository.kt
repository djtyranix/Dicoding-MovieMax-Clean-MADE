package com.nixstudio.moviemax.core.data

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.nixstudio.moviemax.core.data.entities.utils.MediaType
import com.nixstudio.moviemax.core.data.sources.local.LocalDataSource
import com.nixstudio.moviemax.core.data.sources.remote.RemoteDataSource
import com.nixstudio.moviemax.core.domain.model.Combined
import com.nixstudio.moviemax.core.domain.model.Favorite
import com.nixstudio.moviemax.core.domain.model.Movie
import com.nixstudio.moviemax.core.domain.model.TvShow
import com.nixstudio.moviemax.core.domain.repository.MovieMaxRepositoryInterface
import com.nixstudio.moviemax.core.utils.DataMapper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class MovieMaxRepository(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource
) :
    MovieMaxRepositoryInterface {

    override fun getDiscoveryMovie(page: Int): Flow<List<Movie>> {
        return remoteDataSource.getMoviesFromDiscovery(page).map {
            DataMapper.mapDiscoverMovieListToMovieList(it)
        }
    }

    override fun getDiscoveryTvShows(page: Int): Flow<List<TvShow>> {
        return remoteDataSource.getTvShowsFromDiscovery(page).map {
            DataMapper.mapDiscoverTvListToTvShowList(it)
        }
    }

    override fun searchByString(query: String, page: Int): Flow<List<Combined>> {
        return remoteDataSource.searchFromString(query, page).map {
            DataMapper.mapCombinedEntityListToCombinedList(it)
        }
    }

    override fun getTrendingToday(): Flow<List<Combined>> {
        return remoteDataSource.getTrendingToday().map {
            DataMapper.mapCombinedEntityListToCombinedList(it)
        }
    }

    override fun getMovieById(id: Long): Flow<Movie> {
        return remoteDataSource.getMovieById(id).map {
            DataMapper.mapMovieEntityToMovie(it)
        }
    }

    override fun getTvShowsById(id: Long): Flow<TvShow> {
        return remoteDataSource.getTvShowsById(id).map {
            DataMapper.mapTvShowsEntityToTvShow(it)
        }
    }

    override fun getAllFavorites(): Flow<PagingData<Favorite>> {
        return Pager(
            PagingConfig(
                pageSize = 5,
                enablePlaceholders = false
            )
        ) {
            localDataSource.getAllFavorites()
        }.flow.map {
            DataMapper.mapFavoriteEntityListToFavoriteList(it)
        }
    }

    override fun getFavoritesFromMediaType(mediaType: MediaType): Flow<PagingData<Favorite>> {
        return Pager(
            PagingConfig(
                pageSize = 5,
                enablePlaceholders = false
            )
        ) {
            localDataSource.getAllFromMediaType(mediaType)
        }.flow.map {
            DataMapper.mapFavoriteEntityListToFavoriteList(it)
        }
    }

    override fun addFavorite(movie: Movie?, tvShow: TvShow?) {
        var favorite: Favorite? = null

        if (movie != null) {
            favorite = movie.id?.let {
                Favorite(
                    itemId = it,
                    mediaType = "movie",
                    title = movie.title,
                    posterPath = movie.posterPath,
                    createdAt = System.currentTimeMillis()
                )
            }
        } else if (tvShow != null) {
            favorite = tvShow.id?.let {
                Favorite(
                    itemId = it,
                    mediaType = "tv",
                    title = tvShow.name,
                    posterPath = tvShow.posterPath,
                    createdAt = System.currentTimeMillis()
                )
            }
        }

        if (favorite != null) {
            localDataSource.addFavorite(favorite)
        }
    }

    override fun removeFavorite(movie: Movie?, tvShow: TvShow?) {
        var favorite: Favorite? = null

        if (movie != null) {
            favorite = movie.id?.let {
                Favorite(
                    itemId = it,
                    mediaType = "movie",
                    title = movie.title,
                    posterPath = movie.posterPath,
                    createdAt = System.currentTimeMillis()
                )
            }
        } else if (tvShow != null) {
            favorite = tvShow.id?.let {
                Favorite(
                    itemId = it,
                    mediaType = "tv",
                    title = tvShow.name,
                    posterPath = tvShow.posterPath,
                    createdAt = System.currentTimeMillis()
                )
            }
        }

        if (favorite != null) {
            localDataSource.removeFavorite(favorite)
        }
    }

    override fun checkIfFavoriteExist(id: Long): Int {
        return localDataSource.checkIfRecordExist(id)
    }
}