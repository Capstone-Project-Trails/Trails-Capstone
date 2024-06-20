package com.bangkitcapstone.trails.ui.favorite

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.bangkitcapstone.trails.adapter.FavoriteAdapter
import com.bangkitcapstone.trails.data.remote.response.Coordinates
import com.bangkitcapstone.trails.data.remote.response.DetailData
import com.bangkitcapstone.trails.databinding.ActivityFavoriteBinding
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.firestore.ktx.firestore

class FavoriteActivity : AppCompatActivity() {
    private lateinit var binding: ActivityFavoriteBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var listFavorites: ArrayList<DetailData>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityFavoriteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = Firebase.auth

        listFavorites = ArrayList()
        val layoutManager = LinearLayoutManager(this)
        binding.rvFavorite.layoutManager = layoutManager

        binding.close.setOnClickListener {
            onBackPressed()
        }

        fetchFavoriteData()
    }

    private fun fetchFavoriteData() {
        val db = com.google.firebase.ktx.Firebase.firestore
        val userId = FirebaseAuth.getInstance().currentUser?.uid

        listFavorites = ArrayList()

        db.collection("users").document(userId.toString()).collection("favorites")
            .get()
            .addOnSuccessListener { result ->
                listFavorites.clear()
                for (document in result) {
                    val doc = document.data
                    val id = doc["place_id"] as String? ?: ""
                    val formattedAddress = doc["formatted_address"] as String? ?: ""
                    val link = doc["link"] as String
                    val rating = doc["rating"] as String
                    val photoUrl = doc["image"] as String? ?: ""
                    val description = doc["description"] as String
                    val userRatingTotal = doc["total_reviews"] as String
                    val title = doc["title"] as String
                    val region = doc["region"] as String? ?: ""
                    val vicinity = doc["vicinity"] as String? ?: ""
                    val coordinatesDoc = doc["coordinates"] as Map<*, *>?
                    val latitude = coordinatesDoc?.get("latitude") as Double? ?: 0.0
                    val longitude = coordinatesDoc?.get("longitude") as Double? ?: 0.0
                    val coordinates = Coordinates(latitude, longitude)

                    val detailData = DetailData(
                        id = id,
                        title = title,
                        rating = rating,
                        userRatingTotal = userRatingTotal,
                        image = photoUrl,
                        formattedAddress = formattedAddress,
                        vicinity = vicinity,
                        region = region,
                        description = description,
                        link = link,
                        coordinates = coordinates
                    )

                    listFavorites.add(detailData)
                }

                if (listFavorites.isEmpty()) {
                    binding.animEmpty.visibility = View.VISIBLE
                    binding.tvEmpty.visibility = View.VISIBLE
                } else {
                    binding.animEmpty.visibility = View.GONE
                    binding.tvEmpty.visibility = View.GONE
                }

                val adapter = FavoriteAdapter()
                adapter.submitList(listFavorites)
                binding.rvFavorite.adapter = adapter
            }
            .addOnFailureListener { exception ->
                Log.w("favorite", "Error getting documents.", exception)
            }
    }

    override fun onResume() {
        super.onResume()
        fetchFavoriteData()
    }
}
