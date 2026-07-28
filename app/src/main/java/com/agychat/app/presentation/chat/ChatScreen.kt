package com.agychat.app.presentation.chat

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.agychat.app.presentation.common.AgyChatTopBar

@Composable
fun ChatScreen(
    viewModel: ChatViewModel = hiltViewModel(),
    onNavigateBack: () -> Unit,
    onNavigateToPlan: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()

    Scaffold(
        topBar = {
            AgyChatTopBar(
                title = uiState.sessionTitle,
                onNavigateBack = onNavigateBack
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            ChatStatusHeader(
                model = uiState.currentModel,
                connectionState = uiState.connectionState
            )
            ChatMessageList(
                messages = uiState.messages,
                modifier = Modifier.weight(1f),
                onEvent = viewModel::onEvent
            )
            ChatInputBar(
                onSend = { viewModel.onEvent(ChatUiEvent.SendMessage(it)) }
            )
        }
    }
}
