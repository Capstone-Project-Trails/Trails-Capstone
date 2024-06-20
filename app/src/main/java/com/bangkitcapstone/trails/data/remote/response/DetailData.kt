package com.bangkitcapstone.trails.data.remote.response

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class DetailData(
    val id: String,
    val title: String,
    val rating: String,
    val userRatingTotal: String,
    val image: String? = null,
    val formattedAddress: String? = null,
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
