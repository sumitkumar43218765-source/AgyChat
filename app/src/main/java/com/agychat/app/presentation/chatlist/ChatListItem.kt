package com.agychat.app.presentation.chatlist

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.agychat.app.domain.model.ChatSession
import com.agychat.app.presentation.theme.AgySurfaceElevatedDark
import com.agychat.app.presentation.theme.Dimens
import com.agychat.app.presentation.theme.Shape

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ChatListItem(
    session: ChatSession,
    onClick: () -> Unit,
    onDelete: () -> Unit,
    onRename: (String) -> Unit
) {
    var showActions by remember { mutableStateOf(false) }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .combinedClickable(
                onClick = onClick,
                onLongClick = { showActions = true }
            ),
        colors = CardDefaults.cardColors(containerColor = AgySurfaceElevatedDark),
        shape = Shape.CardShape
    ) {
        Column(modifier = Modifier.padding(Dimens.SpacingMd)) {
            Text(text = session.title, fontWeight = FontWeight.Bold, style = MaterialTheme.typography.titleMedium)
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = session.lastMessagePreview ?: "No messages", style = MaterialTheme.typography.bodyMedium)
        }
        
        if (showActions) {
            ChatListItemActions(
                onDismiss = { showActions = false },
                onRename = { onRename("New Title"); showActions = false }, // Simple mock for rename
                onDelete = { onDelete(); showActions = false }
            )
        }
    }
}
