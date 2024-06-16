package com.bangkitcapstone.trails.ui.onboarding

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bangkitcapstone.trails.R
import com.bangkitcapstone.trails.databinding.FragmentOnboardingBinding
import com.bangkitcapstone.trails.ui.welcome.WelcomeActivity

class OnboardingFragment : Fragment() {
    private var _binding: FragmentOnboardingBinding? = null
    private val binding get() = _binding as FragmentOnboardingBinding
    private var position = 0
    private var username = ""
    private var viewPagerNavigation: ViewPagerNavigation? = null

    interface ViewPagerNavigation {
        fun navigateToPage(position: Int)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is ViewPagerNavigation) {
            viewPagerNavigation = context
        } else {
            throw RuntimeException("$context must implement ViewPagerNavigation")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentOnboardingBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments?.let {
            position = it.getInt(ARG_SECTION_NUMBER)
            username = it.getString(ARG_NAME).toString()
        }

        when (position) {
            1 -> onboardingPage(
                R.drawable.onboarding_location,
                R.string.title_onboarding_1,
                R.string.desc_onboarding_1,
                true,
                null,
                false
            )

            2 -> onboardingPage(
                R.drawable.onboarding_search,
                R.string.title_onboarding_2,
                R.string.desc_onboarding_2,
                false,
                "Next",
                true
            )

            3 -> onboardingPage(
                R.drawable.onboarding_ai,
                R.string.title_onboarding_3,
                R.string.desc_onboarding_3,
                false,
                "Get Started",
                true
            )
        }
    }

    private fun onboardingPage(
        imageResId: Int,
        titleResId: Int,
        descResId: Int,
        fullButtonVisible: Boolean,
        halfButtonText: String?,
        backButtonVisible: Boolean
    ) {
        binding.imageOnboarding.setImageResource(imageResId)
        binding.titleOnboarding.text = getString(titleResId)
        binding.descOnboarding.text = getString(descResId)

        binding.fullButton.visibility = if (fullButtonVisible) View.VISIBLE else View.GONE
        binding.halfButton.visibility = if (!fullButtonVisible) View.VISIBLE else View.GONE
        binding.halfButton.text = halfButtonText
        binding.halfButton.contentDescription = halfButtonText
        binding.backButton.visibility = if (backButtonVisible) View.VISIBLE else View.GONE

        binding.fullButton.setOnClickListener {
            navigateToNextPage()
        }

        binding.halfButton.setOnClickListener {
            navigateToNextPage()
        }

        binding.backButton.setOnClickListener {
            navigateToPreviousPage()
        }
    }

    private fun navigateToNextPage() {
        viewPagerNavigation?.let {
            if (position < 3) {
                it.navigateToPage(position)
            } else {
                val intent = Intent(activity, WelcomeActivity::class.java)
                startActivity(intent)
            }
        }
    }

    private fun navigateToPreviousPage() {
        viewPagerNavigation?.let {
            if (position > 1) {
                it.navigateToPage(position - 2)
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    companion object {
        const val ARG_SECTION_NUMBER = "section_number"
        const val ARG_NAME = "app_name"
    }
}
