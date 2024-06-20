package com.bangkitcapstone.trails.ui.detail

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.bangkitcapstone.trails.R
import com.bangkitcapstone.trails.databinding.ActivityDetailBinding
import com.bangkitcapstone.trails.data.remote.response.DetailData
import com.bumptech.glide.Glide
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class DetailActivity : AppCompatActivity(), OnMapReadyCallback {
    private lateinit var binding: ActivityDetailBinding
    private lateinit var mMap: GoogleMap
    private var lat: Double = 0.0
    private var long: Double = 0.0
    private lateinit var title: String
    private val db = Firebase.firestore


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val userId = FirebaseAuth.getInstance().currentUser?.uid.toString()

        @Suppress("DEPRECATION")
        val item = intent.getParcelableExtra<DetailData>(DATA)

        binding.circle.setOnClickListener {
            onBackPressed()
        }

        if (item != null) {
            val docRef =
                db.collection("users").document(userId).collection("favorites").document(item.id)
            docRef.get().addOnSuccessListener { document ->
                if (document.exists()) {
                    binding.wishlist.setImageResource(R.drawable.baseline_favorite_24)
                    binding.circleWishlist.setOnClickListener {
                        removeFromFavorites(item, userId)
                    }
                } else {
                    binding.wishlist.setImageResource(R.drawable.baseline_favorite_border_24)
                    binding.circleWishlist.setOnClickListener {
                        addToFavorites(item, userId)
                    }
                }
            }.addOnFailureListener { exception ->
                Log.w("firestore", "Error getting document: ", exception)
            }

            binding.detailTitle.text = item.title
            binding.detailRating.text = item.rating
            binding.description.text = item.description
            Glide.with(this)
                .load(item.image)
                .timeout(10000)
                .into(binding.detailImage)
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

    private fun addToFavorites(item: DetailData, userId: String) {
        val user = hashMapOf(
            "place_id" to item.id,
            "title" to item.title,
            "rating" to item.rating,
            "description" to item.description,
            "image" to item.image,
            "total_reviews" to item.userRatingTotal,
            "lat" to item.coordinates.latitude,
            "lon" to item.coordinates.longitude,
            "link" to item.link,
            "vicinity" to item.vicinity,
            "formatted_address" to item.formattedAddress,
            "region" to item.region
        )

        db.collection("users").document(userId).collection("favorites").document(item.id)
            .set(user)
            .addOnSuccessListener {
                Log.d("firestore", "DocumentSnapshot added with ID: $userId")
                binding.wishlist.setImageResource(R.drawable.baseline_favorite_24)
                Toast.makeText(
                    this, "Successfully added favorite", Toast.LENGTH_SHORT
                ).show()
                binding.circleWishlist.setOnClickListener {
                    removeFromFavorites(item, userId)
                }
            }
            .addOnFailureListener { e ->
                Log.w("firestore", "Error adding document", e)
            }
    }

    private fun removeFromFavorites(item: DetailData, userId: String) {
        db.collection("users").document(userId).collection("favorites").document(item.id)
            .delete()
            .addOnSuccessListener {
                Log.d("firestore", "DocumentSnapshot successfully deleted!")
                binding.wishlist.setImageResource(R.drawable.baseline_favorite_border_24)
                Toast.makeText(
                    this, "Successfully removed favorite", Toast.LENGTH_SHORT
                ).show()
                binding.circleWishlist.setOnClickListener {
                    addToFavorites(item, userId)
                }
            }
            .addOnFailureListener { e ->
                Log.w("firestore", "Error deleting document", e)
            }
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
