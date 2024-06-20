package com.bangkitcapstone.trails.ui.aichat

import androidx.lifecycle.ViewModel
import com.bangkitcapstone.trails.data.repository.TrailsRepository

class AIChatViewModel(private val repository: TrailsRepository) : ViewModel() {
    fun getResponse(message: String) =
        repository.getAIResponse(message)
}
