package com.agychat.app.presentation.chat.bubble

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.agychat.app.domain.model.ThinkingBlockContent
import com.agychat.app.presentation.theme.BubbleThinking
import com.agychat.app.presentation.theme.Dimens
import com.agychat.app.presentation.theme.Shape

@Composable
fun ThinkingCollapsible(
    content: ThinkingBlockContent,
    isExpanded: Boolean,
    onToggle: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = Dimens.spacingS)
            .background(BubbleThinking, Shape.CardShape)
            .clickable { onToggle() }
            .padding(Dimens.spacingM)
    ) {
        if (isExpanded) {
            Text(text = content.summary + "\nTokens: ${content.tokens}")
        } else {
            Text(text = "💭 Thinking... ${content.timeMs}ms")
        }
    }
}
