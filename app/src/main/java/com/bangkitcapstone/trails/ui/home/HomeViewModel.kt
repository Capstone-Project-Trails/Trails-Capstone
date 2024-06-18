package com.bangkitcapstone.trails.ui.home

import androidx.lifecycle.ViewModel
import com.bangkitcapstone.trails.data.repository.TrailsRepository

class HomeViewModel(private val repository: TrailsRepository) : ViewModel() {
    fun popularDestination() =
        repository.getPopularDestination()

    fun nearbyDestination(lat: String, lon: String) =
        repository.getNearbyDestination(lat, lon)
}
