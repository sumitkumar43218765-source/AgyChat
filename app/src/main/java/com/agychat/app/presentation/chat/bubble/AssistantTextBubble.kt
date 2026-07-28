package com.agychat.app.presentation.chat.bubble

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.agychat.app.domain.model.AssistantTextContent
import com.agychat.app.presentation.theme.BubbleAssistant
import com.agychat.app.presentation.theme.BubbleShapeAssistant
import com.agychat.app.presentation.theme.Dimens

@Composable
fun AssistantTextBubble(content: AssistantTextContent) {
    Box(
        modifier = Modifier.fillMaxWidth(),
        contentAlignment = Alignment.CenterStart
    ) {
        Box(
            modifier = Modifier
                .background(BubbleAssistant, BubbleShapeAssistant)
                .padding(Dimens.spacingM)
        ) {
            Text(text = content.text, color = Color.LightGray)
        }
    }
}
