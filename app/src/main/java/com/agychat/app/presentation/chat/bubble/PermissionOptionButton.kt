package com.agychat.app.presentation.chat.bubble

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.agychat.app.domain.model.PermissionOption
import com.agychat.app.presentation.theme.AgyPrimary
import com.agychat.app.presentation.theme.Dimens
import com.agychat.app.presentation.theme.ChipShape

@Composable
fun PermissionOptionButton(
    option: PermissionOption,
    isSelected: Boolean,
    onSelect: () -> Unit
) {
    val modifier = if (isSelected) Modifier.border(2.dp, AgyPrimary, ChipShape) else Modifier
    Box(
        modifier = modifier
            .fillMaxWidth()
            .clickable(onClick = onSelect)
            .padding(Dimens.SpacingSm)
    ) {
        Text(text = option.label)
    }
}
