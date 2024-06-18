package com.bangkitcapstone.trails.data.remote.response

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

data class DestinationResponse(

    @field:SerializedName("popularDestinations")
    val popularDestinations: List<PopularDestinationsItem>,

    @field:SerializedName("error")
    val error: Boolean? = null,

    @field:SerializedName("message")
    val message: String? = null,

    @field:SerializedName("nearby")
    val nearby: List<NearbyItem>,
)

@Parcelize
data class NearbyItem(

    @field:SerializedName("types")
    val types: String? = null,

    @field:SerializedName("distance")
    val distance: String? = null,

    @field:SerializedName("rating")
    val rating: String,

    @field:SerializedName("description")
    val description: String,

    @field:SerializedName("lon")
    val lon: Double,

    @field:SerializedName("photos")
    val photos: String? = null,

    @field:SerializedName("locationUrl")
    val locationUrl: String,

    @field:SerializedName("name")
    val name: String,

    @field:SerializedName("user_rating_total")
    val userRatingTotal: String,

    @field:SerializedName("vicinity")
    val vicinity: String,

    @field:SerializedName("region")
    val region: String? = null,

    @field:SerializedName("place_id")
    val placeId: String? = null,

    @field:SerializedName("lat")
    val lat: Double,
) : Parcelable

@Parcelize
data class PopularDestinationsItem(

    @field:SerializedName("locationUrl")
    val locationUrl: String,

    @field:SerializedName("types")
    val types: String? = null,

    @field:SerializedName("name")
    val name: String,

    @field:SerializedName("rating")
    val rating: String,

    @field:SerializedName("user_rating_total")
    val userRatingTotal: String,

    @field:SerializedName("vicinity")
    val vicinity: String? = null,

    @field:SerializedName("lon")
    val lon: Double,

    @field:SerializedName("photos")
    val photos: String? = null,

    @field:SerializedName("place_id")
    val placeId: String? = null,

    @field:SerializedName("lat")
    val lat: Double,
) : Parcelable
