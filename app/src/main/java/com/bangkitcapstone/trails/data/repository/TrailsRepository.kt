package com.bangkitcapstone.trails.data.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.bangkitcapstone.trails.data.remote.response.ResultsItem
import com.bangkitcapstone.trails.data.remote.retrofit.ApiService
import com.bangkitcapstone.trails.utils.Result

class TrailsRepository private constructor(
    private val apiService: ApiService
) {
    fun getSearchDestination(
        query: String,
        range: String,
        type: String,
        region: String
    ): LiveData<Result<List<ResultsItem>>> = liveData {
        emit(Result.Loading)
        try {
            val data = apiService.getSearch(query, range, type, region)
            emit(Result.Success(data.results))

        } catch (e: Exception) {
            Log.d("TrailsRepository", "getSearch: ${e.message.toString()} ")
            emit(Result.Error(e.message.toString()))
        }
    }

    companion object {
        @Volatile
        private var instance: TrailsRepository? = null
        fun getInstance(apiService: ApiService): TrailsRepository =
            instance ?: synchronized(this) {
                instance ?: TrailsRepository(apiService)
            }.also { instance = it }
    }
}
