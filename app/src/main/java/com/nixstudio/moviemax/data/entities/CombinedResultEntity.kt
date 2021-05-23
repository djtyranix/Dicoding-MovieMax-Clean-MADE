package com.nixstudio.moviemax.data.entities

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class CombinedResultEntity(

    @field:SerializedName("overview")
    val overview: String? = null,

    @field:SerializedName("title")
    val title: String? = null,

    @field:SerializedName("poster_path")
    val posterPath: String? = null,

    @field:SerializedName("media_type")
    val mediaType: String? = null,

    @field:SerializedName("vote_average")
    val voteAverage: Double? = null,

    @field:SerializedName("id")
    val id: Long? = null,

    @field:SerializedName("name")
    val name: String? = null
) : Parcelable
