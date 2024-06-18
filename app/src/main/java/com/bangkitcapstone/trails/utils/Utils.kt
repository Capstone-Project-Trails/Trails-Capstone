package com.bangkitcapstone.trails.utils

import com.bangkitcapstone.trails.data.remote.response.NearbyItem
import com.bangkitcapstone.trails.data.remote.response.ResultsItem

fun NearbyItem.toDetailData(): DetailData {
    return DetailData(
        title = this.name,
        rating = this.rating,
        userRatingTotal = this.userRatingTotal,
        vicinity = null,
        description = this.description,
        link = this.locationUrl,
        formattedAddress = this.vicinity,
        region = this.region,
        coordinates = Coordinates(this.lat, this.lon)
    )
}

fun ResultsItem.toDetailData(): DetailData {
    return DetailData(
        title = this.title,
        rating = this.rating,
        userRatingTotal = this.userRatingTotal,
        vicinity = null,
        description = this.description,
        link = this.link,
        formattedAddress = this.formattedAddress,
        region = this.region,
        coordinates = Coordinates(this.coordinates.latitude, this.coordinates.longitude)
    )
}
