package com.nixstudio.moviemax.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Cast(
    val castId: Int? = null,
    val name: String? = null,
    val profilePath: String? = null,
    val id: Int? = null,
) : Parcelable
