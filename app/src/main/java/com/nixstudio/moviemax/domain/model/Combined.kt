package com.nixstudio.moviemax.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Combined(
    val overview: String? = null,
    val title: String? = null,
    val posterPath: String? = null,
    val mediaType: String? = null,
    val voteAverage: Double? = null,
    val id: Long? = null,
    val name: String? = null
) : Parcelable