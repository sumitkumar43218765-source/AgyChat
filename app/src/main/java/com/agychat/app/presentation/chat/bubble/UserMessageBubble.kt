package com.agychat.app.presentation.chat.bubble

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.agychat.app.presentation.theme.BubbleShapeUser
import com.agychat.app.presentation.theme.BubbleUser
import com.agychat.app.presentation.theme.Dimens

@Composable
fun UserMessageBubble(text: String, timestamp: Long) {
    Box(
        modifier = Modifier.fillMaxWidth(),
        contentAlignment = Alignment.CenterEnd
    ) {
        Column(horizontalAlignment = Alignment.End) {
            Box(
                modifier = Modifier
                    .background(BubbleUser, BubbleShapeUser)
                    .padding(Dimens.SpacingMd)
            ) {
                Text(text = text, color = Color.White)
            }
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = timestamp.toString(), color = Color.Gray, modifier = Modifier.padding(end = 4.dp))
        }
    }
}
