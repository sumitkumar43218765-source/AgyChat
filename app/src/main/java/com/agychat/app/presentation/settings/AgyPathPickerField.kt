package com.agychat.app.presentation.settings

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Folder
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.agychat.app.presentation.components.AgyChatTextField
import com.agychat.app.presentation.theme.AgyTextSecondary

@Composable
fun AgyPathPickerField(
    path: String,
    onPathChange: (String) -> Unit
) {
    AgyChatTextField(
        value = path,
        onValueChange = onPathChange,
        label = "agy Binary Path",
        placeholder = "/data/data/com.termux/files/usr/bin/agy",
        trailingIcon = {
            Icon(imageVector = Icons.Default.Folder, contentDescription = "Pick folder", tint = AgyTextSecondary)
        },
        modifier = Modifier.fillMaxWidth()
    )
}
