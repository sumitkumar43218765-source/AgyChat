package com.agychat.app.presentation.chatlist

import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

@Composable
fun ChatListItemActions(
    onDismiss: () -> Unit,
    onRename: () -> Unit,
    onDelete: () -> Unit
) {
    DropdownMenu(
        expanded = true,
        onDismissRequest = onDismiss
    ) {
        DropdownMenuItem(
            text = { Text("Rename") },
            onClick = onRename
        )
        DropdownMenuItem(
            text = { Text("Delete") },
            onClick = onDelete
        )
    }
}
