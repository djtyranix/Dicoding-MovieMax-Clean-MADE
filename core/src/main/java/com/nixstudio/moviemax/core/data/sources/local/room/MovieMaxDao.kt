package com.nixstudio.moviemax.core.data.sources.local.room

import androidx.paging.PagingSource
import androidx.room.*
import com.nixstudio.moviemax.core.data.entities.FavoriteEntity

@Dao
interface MovieMaxDao {
    @Query("SELECT * FROM favorite ORDER BY created_at DESC")
    fun getAll(): PagingSource<Int, FavoriteEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(favorite: FavoriteEntity)

    @Delete
    fun delete(favorite: FavoriteEntity)

    @Query("SELECT * FROM favorite WHERE media_type = :mediaType ORDER BY created_at DESC")
    fun getAllFromMediaType(mediaType: String): PagingSource<Int, FavoriteEntity>

    @Query("SELECT COUNT(item_id) FROM favorite WHERE item_id = :id")
    fun checkIfRecordExist(id: Long): Int
}