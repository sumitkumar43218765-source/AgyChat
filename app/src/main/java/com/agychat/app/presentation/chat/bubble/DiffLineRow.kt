package com.agychat.app.presentation.chat.bubble

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import com.agychat.app.domain.model.DiffLine
import com.agychat.app.domain.model.DiffType
import com.agychat.app.presentation.theme.DiffAdded
import com.agychat.app.presentation.theme.DiffAddedText
import com.agychat.app.presentation.theme.DiffRemoved
import com.agychat.app.presentation.theme.DiffRemovedText

@Composable
fun DiffLineRow(line: DiffLine) {
    val bgColor = when (line.type) {
        DiffType.ADDED -> DiffAdded
        DiffType.REMOVED -> DiffRemoved
        DiffType.CONTEXT -> Color.Transparent
    }
    val textColor = when (line.type) {
        DiffType.ADDED -> DiffAddedText
        DiffType.REMOVED -> DiffRemovedText
        DiffType.CONTEXT -> Color.LightGray
    }
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(bgColor)
            .padding(vertical = 2.dp)
    ) {
        Text(text = line.number.toString(), modifier = Modifier.width(32.dp), color = Color.Gray, fontFamily = FontFamily.Monospace)
        Text(text = line.content, color = textColor, fontFamily = FontFamily.Monospace)
    }
}
