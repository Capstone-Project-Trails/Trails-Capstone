package com.bangkitcapstone.trails.ui.onboarding

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.bangkitcapstone.trails.R
import com.bangkitcapstone.trails.adapter.SectionsPagerAdapter
import com.bangkitcapstone.trails.ui.main.MainActivity
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.tbuonomo.viewpagerdotsindicator.SpringDotsIndicator

class OnboardingActivity : AppCompatActivity(), OnboardingFragment.ViewPagerNavigation {
    lateinit var viewPager: ViewPager2
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_onboarding)

        auth = Firebase.auth
        val firebaseUser = auth.currentUser
        if (firebaseUser != null) {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
            return
        }

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
