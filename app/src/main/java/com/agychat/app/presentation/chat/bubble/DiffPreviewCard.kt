package com.agychat.app.presentation.chat.bubble

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.agychat.app.domain.model.DiffPreviewContent
import com.agychat.app.presentation.theme.Dimens
import com.agychat.app.presentation.theme.Shape

@Composable
fun DiffPreviewCard(
    content: DiffPreviewContent,
    isExpanded: Boolean,
    onToggle: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = Dimens.SpacingSm)
            .clickable { onToggle() }
            .background(androidx.compose.ui.graphics.Color.DarkGray, Shape.CardShape)
            .padding(Dimens.SpacingSm)
    ) {
        Text(text = content.filePath, modifier = Modifier.padding(bottom = Dimens.SpacingSm))
        val linesToShow = if (isExpanded) content.lines else content.lines.take(5)
        linesToShow.forEach { line ->
            DiffLineRow(line = line)
        }
        if (!isExpanded && content.lines.size > 5) {
            Text(text = "... ${content.lines.size - 5} more lines", color = androidx.compose.ui.graphics.Color.Gray)
        }
    }
}
