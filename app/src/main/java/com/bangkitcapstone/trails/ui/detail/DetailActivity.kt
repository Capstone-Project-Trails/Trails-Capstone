package com.bangkitcapstone.trails.ui.detail

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bangkitcapstone.trails.R
import com.bangkitcapstone.trails.databinding.ActivityDetailBinding
import com.bangkitcapstone.trails.utils.DetailData
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

class DetailActivity : AppCompatActivity(), OnMapReadyCallback {
    private lateinit var binding: ActivityDetailBinding
    private lateinit var mMap: GoogleMap
    private var lat: Double = 0.0
    private var long: Double = 0.0
    private lateinit var title: String


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        @Suppress("DEPRECATION")
        val item = intent.getParcelableExtra<DetailData>(DATA)

        binding.arrowBack.setOnClickListener {
            onBackPressed()
        }

        if (item != null) {
            binding.detailTitle.text = item.title
            binding.detailRating.text = item.rating
            binding.description.text = item.description
            binding.detailTotalReviews.text =
                getString(R.string.userRatingTotal, item.userRatingTotal)
            binding.detailLocation.text =
                item.vicinity ?: getString(
                    R.string.address_detail,
                    item.formattedAddress,
                    item.region
                )

            lat = item.coordinates.latitude
            long = item.coordinates.longitude
            title = item.title

            binding.gotomaps.setOnClickListener {
                val intent = Intent(Intent.ACTION_VIEW).apply {
                    data = Uri.parse(item.link)
                }
                startActivity(intent)
            }
        }


        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this@DetailActivity)
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

    companion object {
        private const val DATA = "data_detail_trails"
    }
}
