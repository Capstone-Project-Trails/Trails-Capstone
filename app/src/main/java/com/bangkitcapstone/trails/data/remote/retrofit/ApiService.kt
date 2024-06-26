package com.bangkitcapstone.trails.data.remote.retrofit

import com.bangkitcapstone.trails.data.remote.response.AIChatResponse
import com.bangkitcapstone.trails.data.remote.response.DestinationResponse
import com.bangkitcapstone.trails.data.remote.response.SearchResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("search")
    suspend fun getSearch(
        @Query("name") name: String,
        @Query("ratingRange") ratingRange: String,
        @Query("type") type: String,
        @Query("region") region: String
    ): SearchResponse

    @GET("recommendations")
    suspend fun getPopularDestination(): DestinationResponse

    @GET("recommendations")
    suspend fun getNearbyDestination(
        @Query("lat") lat: String,
        @Query("lon") lon: String
    ): DestinationResponse

    @GET("chat")
    suspend fun getResponse(
        @Query("message") message: String,
    ) : AIChatResponse
}
