package com.bangkitcapstone.trails.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bangkitcapstone.trails.data.remote.response.ResultsItem
import com.bangkitcapstone.trails.databinding.ItemDestinationBinding
import com.bangkitcapstone.trails.ui.detail.DetailActivity

class DestinationAdapter :
    ListAdapter<ResultsItem, DestinationAdapter.MyViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding =
            ItemDestinationBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }

    class MyViewHolder(val binding: ItemDestinationBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: ResultsItem) {
            binding.destName.text = item.title
            binding.destLocation.text = item.region
            binding.destRating.text = item.rating
            binding.destDesc.text = item.description
//            Glide.with(itemView.context).load(item.avatarUrl).into(binding.destImage)
            itemView.setOnClickListener {
                val intent = Intent(itemView.context, DetailActivity::class.java)
                intent.putExtra(DATA, item)
                itemView.context.startActivity(intent)
            }
        }
    }

    companion object {
        private const val DATA = "data_detail_trails"

        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<ResultsItem>() {
            override fun areItemsTheSame(oldItem: ResultsItem, newItem: ResultsItem): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: ResultsItem, newItem: ResultsItem): Boolean {
                return oldItem == newItem
            }
        }
    }
}
