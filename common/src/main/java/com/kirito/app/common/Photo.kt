package com.kirito.app.common

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Photo(
    val id: Int,
    val width: Int,
    val height: Int,
    val photographer: String,
    val originalUrl: String,
    val large2xUrl: String
) : Parcelable
