package com.bangkitcapstone.trails.ui.detail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.annotation.StringRes
import androidx.viewpager2.widget.ViewPager2
import com.bangkitcapstone.trails.data.remote.response.ResultsItem
import com.bangkitcapstone.trails.R
import com.bangkitcapstone.trails.adapter.DetailPagerAdapter
import com.bangkitcapstone.trails.databinding.ActivityDetailBinding
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class DetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        @Suppress("DEPRECATION")
        val data = intent.getParcelableExtra<ResultsItem>(DATA)

        binding.arrowBack.setOnClickListener {
            onBackPressed()
        }

        if (data != null) {
            binding.detailTitle.text = data.title
            binding.detailRating.text = data.rating
            binding.detailTotalReviews.text =
                getString(R.string.userRatingTotal, data.userRatingTotal)
            binding.detailLocation.text =
                getString(R.string.address_detail, data.formattedAddress, data.region)
        }

        val sectionsPagerAdapter = DetailPagerAdapter(this)
        sectionsPagerAdapter.descFrag = data?.description.toString()
        sectionsPagerAdapter.locLinkFrag = data?.link.toString()
        sectionsPagerAdapter.locLatFrag = data?.coordinates?.latitude.toString()
        sectionsPagerAdapter.locLongFrag = data?.coordinates?.longitude.toString()
        sectionsPagerAdapter.locTitleFrag = data?.title.toString()

        val viewPager: ViewPager2 = findViewById(R.id.view_pager)
        viewPager.adapter = sectionsPagerAdapter

        val tabs: TabLayout = findViewById(R.id.tabs)
        TabLayoutMediator(tabs, viewPager) { tab, position ->
            tab.text = resources.getString(TAB_TITLES[position])
        }.attach()

        supportActionBar?.elevation = 0f
    }

    companion object {
        private const val DATA = "data_detail_trails"

        @StringRes
        private val TAB_TITLES = intArrayOf(
            R.string.tab_text_2,
            R.string.tab_text_1,
        )
    }
}
