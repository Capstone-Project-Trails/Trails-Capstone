package com.bangkitcapstone.trails.ui.detail

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bangkitcapstone.trails.databinding.FragmentLocationBinding
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions


class LocationFragment : Fragment(), OnMapReadyCallback {
    //class LocationFragment : Fragment() {
    private lateinit var mMap: GoogleMap

    private var _binding: FragmentLocationBinding? = null
    private val binding get() = _binding as FragmentLocationBinding
    private var link = ""
    private var title = ""
    private var lat = 0.0
    private var long = 0.0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentLocationBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments?.let {
            title = it.getString(ARG_LOCATION_TITLE).toString()
            link = it.getString(ARG_LOCATION_LINK).toString()
            lat = it.getString(ARG_LOCATION_LAT).toString().toDouble()
            long = it.getString(ARG_LOCATION_LONG).toString().toDouble()
        }

        binding.gotomaps.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW).apply {
                data = Uri.parse(link)
            }
            startActivity(intent)
        }

        val mapFragment = childFragmentManager
            .findFragmentById(com.bangkitcapstone.trails.R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

    }


    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        val positionDestination = LatLng(lat, long)
        mMap.addMarker(
            MarkerOptions()
                .position(positionDestination)
                .title(title)
        )

        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(positionDestination, 15f))

    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    companion object {
        const val ARG_LOCATION_LINK = "link map trails"
        const val ARG_LOCATION_LONG = "longitude trails"
        const val ARG_LOCATION_LAT = "latitude trails"
        const val ARG_LOCATION_TITLE = "title trails"
    }
}
