package com.bangkitcapstone.trails.adapter

import android.content.res.ColorStateList
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bangkitcapstone.trails.R

data class Message(var text: String, val isUser: Boolean)

class ChatAdapter(private val messages: List<Message>) : RecyclerView.Adapter<ChatAdapter.MessageViewHolder>() {

    class MessageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvMessage: TextView = itemView.findViewById(R.id.user_message)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MessageViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_chat, parent, false)
        return MessageViewHolder(view)
    }

    override fun onBindViewHolder(holder: MessageViewHolder, position: Int) {
        val message = messages[position]
        holder.tvMessage.text = message.text
        holder.tvMessage.textAlignment = if (message.isUser) View.TEXT_ALIGNMENT_VIEW_END else View.TEXT_ALIGNMENT_VIEW_START

        val backgroundColor = if (message.isUser) {
            ContextCompat.getColor(holder.itemView.context, R.color.blue_button)
        } else {
            ContextCompat.getColor(holder.itemView.context, R.color.gray)
        }
        holder.tvMessage.backgroundTintList = ColorStateList.valueOf(backgroundColor)

        val params = holder.tvMessage.layoutParams as ConstraintLayout.LayoutParams
        if (message.isUser) {
            params.startToStart = ConstraintLayout.LayoutParams.UNSET
            params.endToEnd = ConstraintLayout.LayoutParams.PARENT_ID
        } else {
            params.startToStart = ConstraintLayout.LayoutParams.PARENT_ID
            params.endToEnd = ConstraintLayout.LayoutParams.UNSET
        }

        holder.tvMessage.layoutParams = params
    }

    override fun getItemCount() = messages.size
}