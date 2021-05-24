package com.nixstudio.moviemax.core.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Favorite(
    val itemId: Long,
    val mediaType: String,
    val title: String?,
    val posterPath: String?,
    val createdAt: Long
) : Parcelable
