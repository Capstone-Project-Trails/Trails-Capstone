package com.bangkitcapstone.trails.di

import com.bangkitcapstone.trails.BuildConfig
import com.bangkitcapstone.trails.data.remote.retrofit.ApiConfig
import com.bangkitcapstone.trails.data.repository.TrailsRepository

object Injection {
    fun provideRepository(): TrailsRepository {
        val apiRecommendationService = ApiConfig.getApiService(BuildConfig.BASE_URL_REC)
        val apiChatService = ApiConfig.getApiService(BuildConfig.BASE_URL_CHAT)
        return TrailsRepository.getInstance(apiRecommendationService, apiChatService)
    }
}
