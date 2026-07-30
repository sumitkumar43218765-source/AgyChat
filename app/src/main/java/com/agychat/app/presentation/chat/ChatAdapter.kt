package com.agychat.app.presentation.chat

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.agychat.app.databinding.ItemMessageUserBinding
import com.agychat.app.databinding.ItemMessageAiBinding

class ChatAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val messages = mutableListOf<String>() // Dummy data

    override fun getItemViewType(position: Int): Int {
        return if (position % 2 == 0) 0 else 1 // Dummy logic
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == 0) {
            val binding = ItemMessageUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            UserViewHolder(binding)
        } else {
            val binding = ItemMessageAiBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            AiViewHolder(binding)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val msg = messages[position]
        if (holder is UserViewHolder) {
            holder.binding.textMessage.text = msg
        } else if (holder is AiViewHolder) {
            holder.binding.textMessage.text = msg
        }
    }

    override fun getItemCount(): Int = messages.size

    class UserViewHolder(val binding: ItemMessageUserBinding) : RecyclerView.ViewHolder(binding.root)
    class AiViewHolder(val binding: ItemMessageAiBinding) : RecyclerView.ViewHolder(binding.root)
}
