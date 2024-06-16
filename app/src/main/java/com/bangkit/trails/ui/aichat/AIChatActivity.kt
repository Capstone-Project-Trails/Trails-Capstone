package com.bangkitcapstone.trails.ui.aichat

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bangkitcapstone.trails.databinding.ActivityAichatBinding

class AIChatActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAichatBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityAichatBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.close.setOnClickListener {
            onBackPressed()
        }
    }

}
