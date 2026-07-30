package com.agychat.app.presentation.chatlist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.agychat.app.databinding.ItemChatSessionBinding
import com.agychat.app.domain.model.ChatSession
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class ChatListAdapter(private val onClick: (ChatSession) -> Unit) :
    ListAdapter<ChatSession, ChatListAdapter.ChatSessionViewHolder>(ChatSessionDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatSessionViewHolder {
        val binding = ItemChatSessionBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ChatSessionViewHolder(binding, onClick)
    }

    override fun onBindViewHolder(holder: ChatSessionViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class ChatSessionViewHolder(
        private val binding: ItemChatSessionBinding,
        private val onClick: (ChatSession) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        private val dateFormat = SimpleDateFormat("MMM dd, yyyy HH:mm", Locale.getDefault())

        fun bind(session: ChatSession) {
            binding.textTitle.text = session.title
            binding.textDate.text = dateFormat.format(Date(session.updatedAt))
            binding.root.setOnClickListener {
                onClick(session)
            }
        }
    }
}

class ChatSessionDiffCallback : DiffUtil.ItemCallback<ChatSession>() {
    override fun areItemsTheSame(oldItem: ChatSession, newItem: ChatSession): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: ChatSession, newItem: ChatSession): Boolean {
        return oldItem == newItem
    }
}
