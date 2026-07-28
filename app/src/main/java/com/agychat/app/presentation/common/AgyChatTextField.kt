package com.agychat.app.presentation.common

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.agychat.app.presentation.theme.AgyPrimary
import com.agychat.app.presentation.theme.Dimens
import com.agychat.app.presentation.theme.TextPrimary
import com.agychat.app.presentation.theme.TextSecondary
import com.agychat.app.presentation.theme.SurfaceDark

@Composable
fun AgyChatTextField(
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String,
    modifier: Modifier = Modifier,
    trailingIcon: @Composable (() -> Unit)? = null,
    maxLines: Int = 1
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        modifier = modifier,
        placeholder = { Text(text = placeholder, color = TextSecondary) },
        trailingIcon = trailingIcon,
        maxLines = maxLines,
        shape = RoundedCornerShape(Dimens.RadiusMedium),
        colors = OutlinedTextFieldDefaults.colors(
            focusedTextColor = TextPrimary,
            unfocusedTextColor = TextPrimary,
            focusedContainerColor = SurfaceDark,
            unfocusedContainerColor = SurfaceDark,
            focusedBorderColor = AgyPrimary,
            unfocusedBorderColor = SurfaceDark,
            cursorColor = AgyPrimary
        )
    )
}
