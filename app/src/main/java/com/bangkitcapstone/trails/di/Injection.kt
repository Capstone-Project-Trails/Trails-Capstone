package com.bangkitcapstone.trails.di

import com.bangkitcapstone.trails.data.remote.retrofit.ApiConfig
import com.bangkitcapstone.trails.data.repository.TrailsRepository

object Injection {
    fun provideRepository(): TrailsRepository {
        val apiService = ApiConfig.getApiService()
        return TrailsRepository.getInstance(apiService)
    }
}
