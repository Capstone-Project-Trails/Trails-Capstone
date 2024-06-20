package com.bangkitcapstone.trails.data.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.bangkitcapstone.trails.data.remote.response.AIChatResponse
import com.bangkitcapstone.trails.data.remote.response.NearbyItem
import com.bangkitcapstone.trails.data.remote.response.PopularDestinationsItem
import com.bangkitcapstone.trails.data.remote.response.ResultsItem
import com.bangkitcapstone.trails.data.remote.retrofit.ApiService
import com.bangkitcapstone.trails.utils.Result

class TrailsRepository private constructor(
    private val apiRecommendationService: ApiService,
    private val apiChatService: ApiService
) {
    fun getSearchDestination(
        query: String,
        range: String,
        type: String,
        region: String
    ): LiveData<Result<List<ResultsItem>>> = liveData {
        emit(Result.Loading)
        try {
            val data = apiRecommendationService.getSearch(query, range, type, region)
            emit(Result.Success(data.results))

        } catch (e: Exception) {
            Log.d("TrailsRepository", "getSearch: ${e.message.toString()} ")
            emit(Result.Error(e.message.toString()))
        }
    }

    fun getPopularDestination(): LiveData<Result<List<PopularDestinationsItem>>> = liveData {
        emit(Result.Loading)
        try {
            val data = apiRecommendationService.getPopularDestination()
            emit(Result.Success(data.popularDestinations))

        } catch (e: Exception) {
            Log.d("TrailsRepository", "getPopular: ${e.message.toString()} ")
            emit(Result.Error(e.message.toString()))
        }
    }

    fun getNearbyDestination(
        lat: String,
        lon: String
    ): LiveData<Result<List<NearbyItem>>> = liveData {
        emit(Result.Loading)
        try {
            val data = apiRecommendationService.getNearbyDestination(lat, lon)
            emit(Result.Success(data.nearby))

        } catch (e: Exception) {
            Log.d("TrailsRepository", "getNearby: ${e.message.toString()} ")
            emit(Result.Error(e.message.toString()))
        }
    }

    fun getAIResponse(
        message: String
    ): LiveData<Result<AIChatResponse>> = liveData {
        emit(Result.Loading)
        try {
            val data = apiChatService.getResponse(message)
            emit(Result.Success(data))

        } catch (e: Exception) {
            Log.d("TrailsRepository", "getAIResponse: ${e.message.toString()} ")
            emit(Result.Error(e.message.toString()))
        }
    }

    companion object {
        @Volatile
        private var instance: TrailsRepository? = null
        fun getInstance(
            apiRecommendationService: ApiService,
            apiChatService: ApiService
        ): TrailsRepository =
            instance ?: synchronized(this) {
                instance ?: TrailsRepository(apiRecommendationService, apiChatService)
            }.also { instance = it }
    }
}
