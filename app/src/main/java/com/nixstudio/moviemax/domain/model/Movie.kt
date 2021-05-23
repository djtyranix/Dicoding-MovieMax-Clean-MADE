package com.nixstudio.moviemax.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Movie(
    val id: Long?,
    val title: String? = null,
    val backdropPath: String? = null,
    val reviews: List<Review>? = null,
    val credits: List<Cast>? = null,
    val genres: List<Genre>? = null,
    val popularity: Double? = null,
    val overview: String? = null,
    val runtime: Int? = null,
    val posterPath: String? = null,
    val releaseDate: String? = null,
    val voteAverage: Double? = null,
) : Parcelable