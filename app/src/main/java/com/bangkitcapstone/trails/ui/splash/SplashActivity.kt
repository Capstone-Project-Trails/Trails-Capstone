package com.bangkitcapstone.trails.ui.splash

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.bangkitcapstone.trails.R
import com.bangkitcapstone.trails.ui.onboarding.OnboardingActivity

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        Handler().postDelayed({
            val i = Intent(
                this@SplashActivity,
                OnboardingActivity::class.java
            )

            startActivity(i)
            finish()
        }, SPLASH_SCREEN_DELAY)
    }

    companion object {
        const val SPLASH_SCREEN_DELAY: Long = 1000
    }
}
