package com.bangkitcapstone.trails.utils

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class DetailData(
    val title: String,
    val rating: String,
    val userRatingTotal: String,
    val formattedAddress: String,
    val vicinity: String? = null,
    val region: String? = null,
    val description: String,
    val link: String,
    val coordinates: Coordinates,
) : Parcelable

@Parcelize
data class Coordinates(
    val latitude: Double,
    val longitude: Double
) : Parcelable
