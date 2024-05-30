package com.bangkit.trails.ui.onboarding

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.bangkit.trails.R
import com.bangkit.trails.adapter.SectionsPagerAdapter
import com.tbuonomo.viewpagerdotsindicator.SpringDotsIndicator

class OnboardingActivity : AppCompatActivity(), OnboardingFragment.ViewPagerNavigation {
    lateinit var viewPager: ViewPager2
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_onboarding)

        val sectionsPagerAdapter = SectionsPagerAdapter(this)
        viewPager = findViewById(R.id.view_pager)
        viewPager.adapter = sectionsPagerAdapter

        val springDotsIndicator = findViewById<SpringDotsIndicator>(R.id.tabs)
        springDotsIndicator.attachTo(viewPager)

        supportActionBar?.elevation = 0f
    }

    override fun navigateToPage(position: Int) {
        viewPager.currentItem = position
    }
}