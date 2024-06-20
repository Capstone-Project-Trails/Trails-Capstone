package com.bangkitcapstone.trails.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bangkitcapstone.trails.data.remote.response.PopularDestinationsItem
import com.bangkitcapstone.trails.databinding.ItemPopularBinding
import com.bangkitcapstone.trails.ui.detail.DetailActivity
import com.bangkitcapstone.trails.utils.toDetailData
import com.bumptech.glide.Glide

class PopularAdapter :
    ListAdapter<PopularDestinationsItem, PopularAdapter.MyViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding =
            ItemPopularBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }

    class MyViewHolder(val binding: ItemPopularBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: PopularDestinationsItem) {
            binding.popularTitle.text = item.name
            binding.popularLoc.text = item.region
            Glide.with(itemView.context)
                .load(item.photos)
                .timeout(10000)
                .into(binding.popularImage)
            itemView.setOnClickListener {
                val intent = Intent(itemView.context, DetailActivity::class.java)
                val detailData = item.toDetailData()
                intent.putExtra(DATA, detailData)
                itemView.context.startActivity(intent)
            }
        }
    }

    companion object {
        private const val DATA = "data_detail_trails"

        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<PopularDestinationsItem>() {
            override fun areItemsTheSame(
                oldItem: PopularDestinationsItem,
                newItem: PopularDestinationsItem
            ): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(
                oldItem: PopularDestinationsItem,
                newItem: PopularDestinationsItem
            ): Boolean {
                return oldItem == newItem
            }
        }
    }
}
