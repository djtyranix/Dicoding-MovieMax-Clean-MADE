package com.nixstudio.moviemax.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class TvShow(
    val id: Long?,
    val numberOfEpisodes: Int? = null,
    val backdropPath: String? = null,
    val reviews: List<Review>? = null,
    val credits: List<Cast>? = null,
    val genres: List<Genre>? = null,
    val popularity: Double? = null,
    val numberOfSeasons: Int? = null,
    val firstAirDate: String? = null,
    val overview: String? = null,
    val posterPath: String? = null,
    val voteAverage: Double? = null,
    val name: String? = null,
    val lastAirDate: String? = null,
) : Parcelable