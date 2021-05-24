package com.nixstudio.moviemax.core.data.sources.local

import androidx.paging.PagingSource
import com.nixstudio.moviemax.core.data.entities.FavoriteEntity
import com.nixstudio.moviemax.core.data.entities.utils.MediaType
import com.nixstudio.moviemax.core.data.sources.local.room.MovieMaxDao
import com.nixstudio.moviemax.core.domain.model.Favorite
import com.nixstudio.moviemax.core.utils.DataMapper

class LocalDataSource(
    private val movieMaxDao: MovieMaxDao
) {

    fun getAllFavorites(): PagingSource<Int, FavoriteEntity> = movieMaxDao.getAll()

    fun getAllFromMediaType(mediaType: MediaType): PagingSource<Int, FavoriteEntity> {
        return if (mediaType == MediaType.MOVIE) {
            movieMaxDao.getAllFromMediaType("movie")
        } else {
            movieMaxDao.getAllFromMediaType("tv")
        }
    }

    fun addFavorite(favorite: Favorite) {
        try {
            movieMaxDao.insert(DataMapper.mapFavoriteToFavoriteEntity(favorite))
        } catch (e: InterruptedException) {
            e.printStackTrace()
        }
    }

    fun removeFavorite(favorite: Favorite) {
        try {
            movieMaxDao.delete(DataMapper.mapFavoriteToFavoriteEntity(favorite))
        } catch (e: InterruptedException) {
            e.printStackTrace()
        }
    }

    fun checkIfRecordExist(id: Long): Int = movieMaxDao.checkIfRecordExist(id)
}