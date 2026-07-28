package com.agychat.app.presentation.common

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.agychat.app.presentation.theme.AgyPrimary
import com.agychat.app.presentation.theme.AgySurfaceDark
import com.agychat.app.presentation.theme.AgyTextPrimary
import com.agychat.app.presentation.theme.AgyTextSecondary
import com.agychat.app.presentation.theme.Dimens

@Composable
fun AgyChatTextField(
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String = "",
    label: String? = null,
    modifier: Modifier = Modifier,
    trailingIcon: @Composable (() -> Unit)? = null,
    maxLines: Int = 1
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        modifier = modifier,
        label = label?.let { { Text(text = it, color = AgyTextSecondary) } },
        placeholder = if (placeholder.isNotEmpty()) { { Text(text = placeholder, color = AgyTextSecondary) } } else null,
        trailingIcon = trailingIcon,
        maxLines = maxLines,
        shape = RoundedCornerShape(Dimens.PaddingSmall),
        colors = OutlinedTextFieldDefaults.colors(
            focusedTextColor = AgyTextPrimary,
            unfocusedTextColor = AgyTextPrimary,
            focusedContainerColor = AgySurfaceDark,
            unfocusedContainerColor = AgySurfaceDark,
            focusedBorderColor = AgyPrimary,
            unfocusedBorderColor = AgySurfaceDark,
            cursorColor = AgyPrimary
        )
    )
}
