package com.bangkitcapstone.trails.data.remote.retrofit

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

}
