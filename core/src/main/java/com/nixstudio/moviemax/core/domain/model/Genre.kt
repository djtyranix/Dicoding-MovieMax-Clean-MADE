package com.nixstudio.moviemax.core.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Genre(
    val name: String? = null,
    val id: Int? = null
) : Parcelable
