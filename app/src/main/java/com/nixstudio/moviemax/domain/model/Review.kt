package com.nixstudio.moviemax.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Review(
    val id: String? = null,
    val content: String? = null,
    val avatarPath: String? = null,
    val name: String? = null,
    val rating: Double? = null,
) : Parcelable
