package com.bangkitcapstone.trails.ui.home

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Geocoder
import android.location.Location
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.bangkitcapstone.trails.R
import com.bangkitcapstone.trails.adapter.NearbyAdapter
import com.bangkitcapstone.trails.adapter.PopularAdapter
import com.bangkitcapstone.trails.data.remote.response.NearbyItem
import com.bangkitcapstone.trails.data.remote.response.PopularDestinationsItem
import com.bangkitcapstone.trails.databinding.FragmentHomeBinding
import com.bangkitcapstone.trails.factory.ViewModelFactory
import com.bangkitcapstone.trails.ui.search.SearchActivity
import com.bangkitcapstone.trails.utils.Result
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import java.util.Locale

class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private lateinit var auth: FirebaseAuth
    private val factory: ViewModelFactory = ViewModelFactory.getInstance()
    private val homeViewModel: HomeViewModel by viewModels {
        factory
    }
    private val binding get() = _binding as FragmentHomeBinding
    private lateinit var fusedLocationClient: FusedLocationProviderClient

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        auth = Firebase.auth
        val user = auth.currentUser

        binding.homeHello.text = getString(R.string.home_hello, user?.displayName)

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireActivity())
        getMyLocation()

        binding.search.setOnClickListener {
            val intent = Intent(requireActivity(), SearchActivity::class.java)
            startActivity(intent)
        }

        val layoutManager = GridLayoutManager(requireActivity(), 2)
        binding.rvPopular.layoutManager = layoutManager

        val nearbylayoutManager =
            LinearLayoutManager(requireActivity(), LinearLayoutManager.HORIZONTAL, false)
        binding.rvNearby.layoutManager = nearbylayoutManager

        homeViewModel.popularDestination().observe(requireActivity()) { result ->
            if (result != null) {
                when (result) {
                    is Result.Loading -> {
                        showLoadingPopular(true)
                    }

                    is Result.Success -> {
                        showLoadingPopular(false)
                        setPopularData(result.data)
                    }

                    is Result.Error -> {
                        showToast(result.error)
                        showLoadingPopular(false)
                    }
                }
            }
        }

    }

    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->
        when {
            permissions[Manifest.permission.ACCESS_FINE_LOCATION] ?: false -> {
                getMyLocation()
            }

            permissions[Manifest.permission.ACCESS_COARSE_LOCATION] ?: false -> {
                getMyLocation()
            }

            else -> {
            }
        }
    }

    private fun checkPermission(permission: String): Boolean {
        return ContextCompat.checkSelfPermission(
            requireActivity(),
            permission
        ) == PackageManager.PERMISSION_GRANTED
    }

    private fun getMyLocation() {
        if (checkPermission(Manifest.permission.ACCESS_FINE_LOCATION) &&
            checkPermission(Manifest.permission.ACCESS_COARSE_LOCATION)
        ) {
            fusedLocationClient.lastLocation.addOnSuccessListener { location: Location? ->
                if (location != null) {
                    val gcd = Geocoder(requireActivity(), Locale.getDefault())
                    val addresses = gcd.getFromLocation(location.latitude, location.longitude, 1)
                    if (addresses!!.size > 0) {
                        binding.rvNearby.visibility = View.VISIBLE
                        binding.nearby.visibility = View.VISIBLE
                        binding.progressBarNearby.visibility = View.VISIBLE

                        binding.location.text =
                            getString(
                                R.string.location_value,
                                addresses[0].subAdminArea,
                                addresses[0].countryName
                            )
                    }
                    homeViewModel.nearbyDestination(
                        location.latitude.toString(), location.longitude.toString()
                    ).observe(requireActivity()) { result ->
                        if (result != null) {
                            when (result) {
                                is Result.Loading -> {
                                    showLoadingNearby(true)
                                }

                                is Result.Success -> {
                                    showLoadingNearby(false)
                                    setNearbyData(result.data)

                                }

                                is Result.Error -> {
                                    showToast(result.error)
                                    showLoadingNearby(false)
                                }
                            }
                        }
                    }
                } else {
                    Toast.makeText(
                        requireActivity(),
                        getString(R.string.location_is_not_found_try_again),
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        } else {
            binding.rvNearby.visibility = View.GONE
            binding.nearby.visibility = View.GONE
            binding.progressBarNearby.visibility = View.GONE

            requestPermissionLauncher.launch(
                arrayOf(
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                )
            )
        }
    }

    private fun setPopularData(listDatas: List<PopularDestinationsItem>) {
        val adapter = PopularAdapter()
        adapter.submitList(listDatas)
        binding.rvPopular.adapter = adapter
    }

    private fun setNearbyData(listDatas: List<NearbyItem>) {
        val adapter = NearbyAdapter()
        adapter.submitList(listDatas)
        binding.rvNearby.adapter = adapter
    }

    private fun showLoadingPopular(isLoading: Boolean) {
        binding.progressBarPopular.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    private fun showLoadingNearby(isLoading: Boolean) {
        binding.progressBarNearby.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    private fun showToast(message: String) {
        Toast.makeText(requireActivity(), message, Toast.LENGTH_SHORT).show()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}
