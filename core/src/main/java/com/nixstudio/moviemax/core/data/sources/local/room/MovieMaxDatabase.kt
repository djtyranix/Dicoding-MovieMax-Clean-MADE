package com.nixstudio.moviemax.core.data.sources.local.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.nixstudio.moviemax.core.data.entities.FavoriteEntity

@Database(entities = [FavoriteEntity::class], version = 1, exportSchema = false)
abstract class MovieMaxDatabase : RoomDatabase() {
    abstract fun movieMaxDao(): MovieMaxDao
}