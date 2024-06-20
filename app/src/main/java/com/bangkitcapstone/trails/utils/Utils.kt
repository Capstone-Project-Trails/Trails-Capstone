package com.bangkitcapstone.trails.utils

import com.bangkitcapstone.trails.data.remote.response.Coordinates
import com.bangkitcapstone.trails.data.remote.response.DetailData
import com.bangkitcapstone.trails.data.remote.response.NearbyItem
import com.bangkitcapstone.trails.data.remote.response.PopularDestinationsItem
import com.bangkitcapstone.trails.data.remote.response.ResultsItem

fun NearbyItem.toDetailData(): DetailData {
    return DetailData(
        id = this.placeId,
        title = this.name,
        rating = this.rating,
        userRatingTotal = this.userRatingTotal,
        vicinity = null,
        image = this.photoUrl,
        description = this.description,
        link = this.locationUrl,
        formattedAddress = this.vicinity,
        region = this.region,
        coordinates = Coordinates(this.lat, this.lon)
    )
}

fun ResultsItem.toDetailData(): DetailData {
    return DetailData(
        id = this.placeId,
        title = this.title,
        rating = this.rating,
        userRatingTotal = this.userRatingTotal,
        vicinity = null,
        description = this.description,
        image = this.photoUrl,
        link = this.link,
        formattedAddress = this.formattedAddress,
        region = this.region,
        coordinates = Coordinates(this.coordinates.latitude, this.coordinates.longitude)
    )
}

fun PopularDestinationsItem.toDetailData(): DetailData {
    return DetailData(
        id = this.placeId,
        title = this.name,
        rating = this.rating,
        userRatingTotal = this.userRatingTotal,
        image = this.photos,
        vicinity = this.vicinity,
        description = this.description,
        link = this.locationUrl,
        formattedAddress = null,
        region = this.region,
        coordinates = Coordinates(lat, lon)
    )
}
