package com.agychat.app.presentation.chat.bubble

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.agychat.app.domain.model.StatusLineContent
import com.agychat.app.presentation.theme.Dimens
import com.agychat.app.presentation.theme.Shape

@Composable
fun StatusLineChip(content: StatusLineContent) {
    Box(
        modifier = Modifier.fillMaxWidth().padding(vertical = Dimens.spacingS),
        contentAlignment = Alignment.Center
    ) {
        Box(
            modifier = Modifier
                .background(Color.DarkGray, Shape.ChipShape)
                .padding(horizontal = Dimens.spacingM, vertical = Dimens.spacingS)
        ) {
            Text(text = "${content.text} · ${content.effort}", color = Color.LightGray)
        }
    }
}
