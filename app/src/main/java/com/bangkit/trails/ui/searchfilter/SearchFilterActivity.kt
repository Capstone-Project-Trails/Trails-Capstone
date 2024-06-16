package com.bangkitcapstone.trails.ui.searchfilter

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bangkitcapstone.trails.databinding.ActivitySearchFilterBinding

class SearchFilterActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySearchFilterBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySearchFilterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.close.setOnClickListener {
            onBackPressed()
        }

        binding.applyButton.setOnClickListener {
            val resultIntent = Intent()
            resultIntent.putExtra(EXTRA_SELECTED_RATING, binding.optRating.text.toString())
            resultIntent.putExtra(EXTRA_SELECTED_TYPE, binding.optType.text.toString())
            resultIntent.putExtra(EXTRA_SELECTED_REGION, binding.optRegion.text.toString())
            setResult(RESULT_CODE, resultIntent)
            finish()
        }

    }

    companion object {
        const val EXTRA_SELECTED_RATING = "extra_selected_rating"
        const val EXTRA_SELECTED_REGION = "extra_selected_region"
        const val EXTRA_SELECTED_TYPE = "extra_selected_type"
        const val RESULT_CODE = 110
    }
}
