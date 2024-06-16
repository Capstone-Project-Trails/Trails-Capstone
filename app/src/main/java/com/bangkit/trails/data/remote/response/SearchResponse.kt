package com.bangkitcapstone.trails.data.remote.response

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

data class SearchResponse(

    @field:SerializedName("error")
    val error: Boolean? = null,

    @field:SerializedName("message")
    val message: String? = null,

    @field:SerializedName("results")
    val results: List<ResultsItem>,
)

@Parcelize
data class Coordinates(
    @field:SerializedName("latitude")
    val latitude: String? = null,

    @field:SerializedName("longitude")
    val longitude: String? = null
) : Parcelable

@Parcelize
data class ResultsItem(

    @field:SerializedName("types")
    val types: String? = null,

    @field:SerializedName("formattedAddress")
    val formattedAddress: String,

    @field:SerializedName("link")
    val link: String? = null,

    @field:SerializedName("rating")
    val rating: String,

    @field:SerializedName("coordinates")
    val coordinates: Coordinates,

    @field:SerializedName("description")
    val description: String,

    @field:SerializedName("user_rating_total")
    val userRatingTotal: String,

    @field:SerializedName("title")
    val title: String,

    @field:SerializedName("region")
    val region: String,

    @field:SerializedName("place_id")
    val placeId: String,
) : Parcelable
