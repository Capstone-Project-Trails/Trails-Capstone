package com.bangkitcapstone.trails.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bangkitcapstone.trails.data.remote.response.NearbyItem
import com.bangkitcapstone.trails.databinding.ItemNearbyBinding
import com.bangkitcapstone.trails.ui.detail.DetailActivity
import com.bangkitcapstone.trails.utils.toDetailData

class NearbyAdapter :
    ListAdapter<NearbyItem, NearbyAdapter.MyViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding =
            ItemNearbyBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }

    class MyViewHolder(val binding: ItemNearbyBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: NearbyItem) {
            binding.nearbyName.text = item.name
            binding.nearbyRating.text = item.rating
            binding.nearbyLocation.text = item.vicinity
//            Glide.with(itemView.context).load(item.avatarUrl).into(binding.destImage)
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

        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<NearbyItem>() {
            override fun areItemsTheSame(oldItem: NearbyItem, newItem: NearbyItem): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: NearbyItem, newItem: NearbyItem): Boolean {
                return oldItem == newItem
            }
        }
    }
}
