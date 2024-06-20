package com.bangkitcapstone.trails.ui.search

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.bangkitcapstone.trails.adapter.DestinationAdapter
import com.bangkitcapstone.trails.data.remote.response.ResultsItem
import com.bangkitcapstone.trails.factory.ViewModelFactory
import com.bangkitcapstone.trails.databinding.ActivitySearchBinding
import com.bangkitcapstone.trails.ui.searchfilter.SearchFilterActivity
import com.bangkitcapstone.trails.utils.Result

class SearchActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySearchBinding

    private val factory: ViewModelFactory = ViewModelFactory.getInstance()
    private val searchViewModel: SearchViewModel by viewModels {
        factory
    }

    private var ratingRange: String = ""
    private var region: String = ""
    private var type: String = ""
    private val resultLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == SearchFilterActivity.RESULT_CODE && result.data != null) {
            val ratingSelectedValue =
                result.data?.getStringExtra(SearchFilterActivity.EXTRA_SELECTED_RATING)
            if (ratingSelectedValue == "All Ranges") {
                ratingRange = ""
            } else {
                ratingRange = ratingSelectedValue.toString()
            }

            val regionSelectedValue =
                result.data?.getStringExtra(SearchFilterActivity.EXTRA_SELECTED_REGION)
            if (regionSelectedValue == "All Regions") {
                region = ""
            } else {
                region = regionSelectedValue.toString()
            }

            val typeSelectedValue =
                result.data?.getStringExtra(SearchFilterActivity.EXTRA_SELECTED_TYPE)
            if (typeSelectedValue == "All Types") {
                type = ""
            } else if (typeSelectedValue == "tourist attraction") {
                type = "tourist_attraction"
            } else {
                type = typeSelectedValue.toString()
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySearchBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val layoutManager = LinearLayoutManager(this)
        binding.rvDestination.layoutManager = layoutManager

        binding.arrowBack.setOnClickListener {
            onBackPressed()
        }

        with(binding) {
            searchView.setupWithSearchBar(searchBar)
            searchView
                .editText
                .setOnEditorActionListener { textView, actionId, event ->
                    searchBar.setText(searchView.text)
                    searchView.hide()
                    searchViewModel.searchDestination(
                        searchView.text.toString(),
                        ratingRange,
                        type,
                        region
                    ).observe(this@SearchActivity) { result ->
                        if (result != null) {
                            when (result) {
                                is Result.Loading -> {
                                    binding.popularDestination.visibility = View.GONE
                                    binding.emptySearch.visibility = View.GONE
                                    binding.rvDestination.visibility = View.GONE
                                    binding.animEmpty.visibility = View.GONE

                                    showLoading(true)
                                }

                                is Result.Success -> {
                                    showLoading(false)
                                    setSearchData(result.data)
                                    binding.rvDestination.visibility = View.VISIBLE

                                    if (result.data.isEmpty()) {
                                        binding.animEmpty.visibility = View.VISIBLE
                                        binding.emptySearch.visibility = View.VISIBLE
                                    } else {
                                        binding.popularDestination.visibility = View.VISIBLE
                                    }
                                }

                                is Result.Error -> {
                                    showToast(result.error)
                                    showLoading(false)
                                }
                            }
                        }
                    }
                    false
                }
        }


        binding.filter.setOnClickListener {
            val intent = Intent(this, SearchFilterActivity::class.java)
            resultLauncher.launch(intent)
        }
    }


    private fun setSearchData(listData: List<ResultsItem>) {
        val adapter = DestinationAdapter()
        adapter.submitList(listData)
        binding.rvDestination.adapter = adapter
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

}
