package com.agychat.app.presentation.chat.bubble

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.graphics.Color
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import com.agychat.app.domain.model.ToolCallContent
import com.agychat.app.presentation.theme.AgySecondary
import com.agychat.app.presentation.theme.BubbleToolCall
import com.agychat.app.presentation.theme.Dimens
import com.agychat.app.presentation.theme.ChipShape

@Composable
fun ToolCallCard(
    content: ToolCallContent,
    isExpanded: Boolean,
    onToggle: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = Dimens.SpacingSm)
            .background(BubbleToolCall, ChipShape)
            .clickable { onToggle() }
            .padding(Dimens.SpacingMd)
    ) {
        Column {
            Text(text = content.actionName, fontWeight = FontWeight.Bold, color = AgySecondary)
            Text(text = content.filePath ?: "")
            if (isExpanded) {
                Text(text = content.args, color = Color.Gray, style = MaterialTheme.typography.bodySmall)
            }
        }
    }
}
