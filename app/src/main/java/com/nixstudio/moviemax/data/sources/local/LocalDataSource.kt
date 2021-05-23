package com.nixstudio.moviemax.data.sources.local

import androidx.lifecycle.MutableLiveData
import androidx.paging.PagingSource
import com.nixstudio.moviemax.data.entities.FavoriteEntity
import com.nixstudio.moviemax.data.sources.local.room.MovieMaxDao
import com.nixstudio.moviemax.data.utils.MediaType
import com.nixstudio.moviemax.domain.model.Favorite
import com.nixstudio.moviemax.utils.DataMapper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch

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

    suspend fun getAllCount(): Int? {
        val count = MutableLiveData<Int>()
        count.value = 0

        coroutineScope {
            launch(Dispatchers.IO) {
                count.postValue(movieMaxDao.getItemCount())
            }
        }

        return count.value
    }
}