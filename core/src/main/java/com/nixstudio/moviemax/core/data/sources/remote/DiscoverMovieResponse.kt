package com.nixstudio.moviemax.core.data.sources.remote

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class DiscoverMovieResponse(

    @field:SerializedName("page")
    val page: Int? = null,

    @field:SerializedName("total_pages")
    val totalPages: Int? = null,

    @field:SerializedName("results")
    val results: List<DiscoverMovieResultsItem?>? = null,

    @field:SerializedName("total_results")
    val totalResults: Int? = null
) : Parcelable

@Parcelize
data class DiscoverMovieResultsItem(

    @field:SerializedName("overview")
    val overview: String? = null,

    @field:SerializedName("title")
    val title: String? = null,

    @field:SerializedName("poster_path")
    val posterPath: String? = null,

    @field:SerializedName("id")
    val id: Long? = null,
) : Parcelable
