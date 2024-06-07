package com.bangkit.trails.ui.searchfilter

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bangkit.trails.databinding.ActivitySearchBinding

class SearchFilterActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySearchBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySearchBinding.inflate(layoutInflater)
        setContentView(binding.root)


    }
}
