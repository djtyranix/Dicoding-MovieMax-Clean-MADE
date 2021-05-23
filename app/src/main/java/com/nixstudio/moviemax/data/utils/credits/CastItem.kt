package com.nixstudio.moviemax.data.utils.credits

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class CastItem(

    @field:SerializedName("cast_id")
    val castId: Int? = null,

    @field:SerializedName("name")
    val name: String? = null,

    @field:SerializedName("profile_path")
    val profilePath: String? = null,

    @field:SerializedName("id")
    val id: Int? = null,
) : Parcelable
