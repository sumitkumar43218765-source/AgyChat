package com.agychat.app.presentation.common

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.agychat.app.presentation.theme.Dimens
import com.agychat.app.presentation.theme.GradientPrimaryEnd
import com.agychat.app.presentation.theme.GradientPrimaryStart
import com.agychat.app.presentation.theme.AgyTextPrimary

@Composable
fun AgyChatButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    isLoading: Boolean = false
) {
    val gradient = Brush.horizontalGradient(
        colors = listOf(GradientPrimaryStart, GradientPrimaryEnd)
    )

    Button(
        onClick = {
            if (!isLoading) onClick()
        },
        modifier = modifier.clip(RoundedCornerShape(12.dp)),
        enabled = enabled,
        contentPadding = PaddingValues(),
        colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent)
    ) {
        Box(
            modifier = Modifier
                .background(if (enabled) gradient else Brush.linearGradient(listOf(Color.Gray, Color.Gray)))
                .padding(horizontal = Dimens.SpacingMd, vertical = Dimens.SpacingSm),
            contentAlignment = Alignment.Center
        ) {
            if (isLoading) {
                CircularProgressIndicator(
                    color = AgyTextPrimary,
                    modifier = Modifier.size(20.dp),
                    strokeWidth = 2.dp
                )
            } else {
                Text(
                    text = text,
                    color = AgyTextPrimary,
                    style = MaterialTheme.typography.labelLarge
                )
            }
        }
    }
}
