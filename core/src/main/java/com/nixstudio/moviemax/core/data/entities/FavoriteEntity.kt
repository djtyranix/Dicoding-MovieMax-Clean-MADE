package com.nixstudio.moviemax.core.data.entities

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize
import java.io.Serializable

@Entity(tableName = "favorite")
@Parcelize
data class FavoriteEntity(
    @PrimaryKey
    @ColumnInfo(name = "item_id")
    val itemId: Long,

    @ColumnInfo(name = "media_type") val mediaType: String,

    @ColumnInfo(name = "title") val title: String?,

    @ColumnInfo(name = "poster_path") val posterPath: String?,

    @ColumnInfo(name = "created_at") val createdAt: Long
) : Serializable, Parcelable