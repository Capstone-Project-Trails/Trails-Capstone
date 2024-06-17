package com.bangkitcapstone.trails.adapter

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.bangkitcapstone.trails.ui.detail.DescriptionFragment
import com.bangkitcapstone.trails.ui.detail.LocationFragment

class DetailPagerAdapter(activity: AppCompatActivity) : FragmentStateAdapter(activity) {

    var descFrag = ""
    var locLinkFrag = ""
    var locLongFrag = ""
    var locLatFrag = ""
    var locTitleFrag = ""

    override fun createFragment(position: Int): Fragment {
        var fragment: Fragment? = null
        when (position) {
            0 -> fragment = LocationFragment()
            1 -> fragment = DescriptionFragment()
        }

        fragment?.arguments = Bundle().apply {
            putString(DescriptionFragment.ARG_DESCRIPTION, descFrag)
            putString(LocationFragment.ARG_LOCATION_LINK, locLinkFrag)
            putString(LocationFragment.ARG_LOCATION_LONG, locLongFrag)
            putString(LocationFragment.ARG_LOCATION_LAT, locLatFrag)
            putString(LocationFragment.ARG_LOCATION_TITLE, locTitleFrag)
        }

        return fragment as Fragment
    }

    override fun getItemCount(): Int {
        return 2
    }
}
