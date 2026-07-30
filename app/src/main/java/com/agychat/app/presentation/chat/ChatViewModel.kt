package com.agychat.app.presentation.chat

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.agychat.app.domain.model.ChatMessage
import com.agychat.app.domain.model.MessageType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class ChatViewModel @Inject constructor() : ViewModel() {
    
    private val _messages = MutableStateFlow<List<ChatMessage>>(emptyList())
    val messages: StateFlow<List<ChatMessage>> = _messages.asStateFlow()

    fun sendMessage(content: String) {
        val userMessage = ChatMessage(
            id = UUID.randomUUID().toString(),
            sessionId = "current_session",
            type = MessageType.USER,
            content = content,
            timestamp = System.currentTimeMillis()
        )
        _messages.value = _messages.value + userMessage
        
        // Dummy response
        val aiMessage = ChatMessage(
            id = UUID.randomUUID().toString(),
            sessionId = "current_session",
            type = MessageType.ASSISTANT_TEXT,
            content = "Echo: $content",
            timestamp = System.currentTimeMillis()
        )
        viewModelScope.launch {
            _messages.value = _messages.value + aiMessage
        }
    }
}
