package com.agychat.app.presentation.chatlist

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import com.agychat.app.presentation.theme.GradientPrimaryEnd
import com.agychat.app.presentation.theme.GradientPrimaryStart

@Composable
fun NewChatFab(onClick: () -> Unit) {
    FloatingActionButton(
        onClick = onClick,
        shape = CircleShape,
        containerColor = Color.Transparent
    ) {
        Box(
            modifier = Modifier.background(
                brush = Brush.linearGradient(listOf(GradientPrimaryStart, GradientPrimaryEnd))
            )
        ) {
            Icon(Icons.Default.Add, contentDescription = "New Chat", tint = Color.White)
        }
    }
}
