package com.bangkitcapstone.trails.ui.search

import androidx.lifecycle.ViewModel
import com.bangkitcapstone.trails.data.repository.TrailsRepository

class SearchViewModel(private val repository: TrailsRepository) : ViewModel() {
    fun searchDestination(query: String, range: String, type: String, region: String) =
        repository.getSearchDestination(query, range, type, region)
}
