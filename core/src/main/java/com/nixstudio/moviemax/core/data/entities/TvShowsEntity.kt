package com.nixstudio.moviemax.core.data.entities

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import com.nixstudio.moviemax.core.data.entities.utils.GenresItem
import com.nixstudio.moviemax.core.data.entities.utils.credits.Credits
import com.nixstudio.moviemax.core.data.entities.utils.reviews.ReviewsResponse
import kotlinx.parcelize.Parcelize

@Parcelize
data class TvShowsEntity(

    @field:SerializedName("id")
    val id: Long?,

    @field:SerializedName("number_of_episodes")
    val numberOfEpisodes: Int? = null,

    @field:SerializedName("backdrop_path")
    val backdropPath: String? = null,

    @field:SerializedName("reviews")
    val reviews: ReviewsResponse.Reviews? = null,

    @field:SerializedName("credits")
    val credits: Credits? = null,

    @field:SerializedName("genres")
    val genres: List<GenresItem?>? = null,

    @field:SerializedName("popularity")
    val popularity: Double? = null,

    @field:SerializedName("number_of_seasons")
    val numberOfSeasons: Int? = null,

    @field:SerializedName("first_air_date")
    val firstAirDate: String? = null,

    @field:SerializedName("overview")
    val overview: String? = null,

    @field:SerializedName("poster_path")
    val posterPath: String? = null,

    @field:SerializedName("vote_average")
    val voteAverage: Double? = null,

    @field:SerializedName("name")
    val name: String? = null,

    @field:SerializedName("last_air_date")
    val lastAirDate: String? = null,
) : Parcelable
