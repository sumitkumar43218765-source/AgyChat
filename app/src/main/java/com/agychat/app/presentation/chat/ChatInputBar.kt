package com.agychat.app.presentation.chat

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.agychat.app.presentation.theme.AgyPrimary
import com.agychat.app.presentation.theme.AgySurfaceElevatedDark
import com.agychat.app.presentation.theme.Dimens

@Composable
fun ChatInputBar(onSend: (String) -> Unit) {
    var text by remember { mutableStateOf("") }

    AnimatedVisibility(visible = true, enter = slideInVertically(initialOffsetY = { it })) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(AgySurfaceElevatedDark)
                .padding(Dimens.SpacingMd),
            verticalAlignment = Alignment.CenterVertically
        ) {
            TextField(
                value = text,
                onValueChange = { text = it },
                modifier = Modifier.weight(1f),
                maxLines = 4,
                placeholder = { Text("Type a message...") },
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = MaterialTheme.colorScheme.surfaceVariant,
                    unfocusedContainerColor = MaterialTheme.colorScheme.surfaceVariant
                )
            )
            Spacer(modifier = Modifier.width(Dimens.Dimens.SpacingSm))
            IconButton(
                onClick = {
                    if (text.isNotBlank()) {
                        onSend(text)
                        text = ""
                    }
                }
            ) {
                Icon(Icons.Default.Send, contentDescription = "Send", tint = AgyPrimary)
            }
        }
    }
}
