package com.nixstudio.moviemax.core.data.entities.utils.credits

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Credits(
    @field:SerializedName("cast")
    val cast: List<CastItem?>? = null,
) : Parcelable
