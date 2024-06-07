package com.bangkit.trails.adapter

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.bangkit.trails.ui.detail.DescriptionFragment
import com.bangkit.trails.ui.detail.LocationFragment
import com.bangkit.trails.ui.detail.ReviewsFragment

class DetailPagerAdapter(activity: AppCompatActivity) : FragmentStateAdapter(activity) {

    override fun createFragment(position: Int): Fragment {
        var fragment: Fragment? = null
        when (position) {
            0 -> fragment = DescriptionFragment()
            1 -> fragment = LocationFragment()
            2 -> fragment = ReviewsFragment()
        }
        return fragment as Fragment
    }

    override fun getItemCount(): Int {
        return 3
    }
}
