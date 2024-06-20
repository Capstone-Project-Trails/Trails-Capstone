package com.bangkitcapstone.trails.data.remote.response

import com.google.gson.annotations.SerializedName

data class AIChatResponse(

	@field:SerializedName("entities")
	val entities: List<String?>? = null,

	@field:SerializedName("response")
	val response: String? = null,

	@field:SerializedName("suggestions")
	val suggestions: List<SuggestionsItem>,

	@field:SerializedName("intent")
	val intent: String? = null
)

data class SuggestionsItem(

	@field:SerializedName("types")
	val types: String? = null,

	@field:SerializedName("user_ratings_total")
	val userRatingsTotal: String? = null,

	@field:SerializedName("latitude")
	val latitude: Double? = null,

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("rating")
	val rating: Double,

	@field:SerializedName("description")
	val description: String? = null,

	@field:SerializedName("vicinity")
	val vicinity: String? = null,

	@field:SerializedName("photo_url")
	val photoUrl: String? = null,

	@field:SerializedName("region")
	val region: String? = null,

	@field:SerializedName("photos")
	val photos: String? = null,

	@field:SerializedName("place_id")
	val placeId: String? = null,

	@field:SerializedName("longitude")
	val longitude: Double? = null
)
