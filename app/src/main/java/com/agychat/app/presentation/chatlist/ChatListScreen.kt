package com.agychat.app.presentation.chatlist

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.agychat.app.presentation.theme.Dimens
import com.agychat.app.presentation.components.AgyChatTopBar

@Composable
fun ChatListScreen(
    viewModel: ChatListViewModel = hiltViewModel(),
    onNavigateToChat: (String) -> Unit,
    onNavigateToSettings: () -> Unit,
    onNavigateToWorkspaces: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()

    Scaffold(
        topBar = {
            AgyChatTopBar(
                title = "AgyChat",
                onSettingsClick = onNavigateToSettings,
                onWorkspacesClick = onNavigateToWorkspaces
            )
        },
        floatingActionButton = {
            NewChatFab(onClick = { viewModel.onEvent(ChatListUiEvent.CreateNewChat) })
        }
    ) { padding ->
        Box(modifier = Modifier.fillMaxSize().padding(padding)) {
            if (uiState.isLoading) {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            } else if (uiState.sessions.isEmpty()) {
                Text("No sessions found.", modifier = Modifier.align(Alignment.Center))
            } else {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(Dimens.spacingM)
                ) {
                    items(uiState.sessions, key = { it.id }) { session ->
                        ChatListItem(
                            session = session,
                            onClick = { onNavigateToChat(session.id) },
                            onDelete = { viewModel.onEvent(ChatListUiEvent.DeleteChat(session.id)) },
                            onRename = { newName -> viewModel.onEvent(ChatListUiEvent.RenameChat(session.id, newName)) }
                        )
                        Spacer(modifier = Modifier.height(Dimens.spacingS))
                    }
                }
            }
        }
    }
}
