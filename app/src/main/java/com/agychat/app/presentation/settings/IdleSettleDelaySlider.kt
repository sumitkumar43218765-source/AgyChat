package com.agychat.app.presentation.settings

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.agychat.app.presentation.theme.AgyPrimary
import com.agychat.app.presentation.theme.AgyTextPrimary
import com.agychat.app.presentation.theme.AgyTextSecondary
import com.agychat.app.presentation.theme.Dimens

@Composable
fun IdleSettleDelaySlider(
    value: Long,
    onValueChange: (Long) -> Unit
) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = "Idle Settle Delay: ${value}ms",
            style = MaterialTheme.typography.bodyLarge,
            color = AgyTextPrimary
        )
        Slider(
            value = value.toFloat(),
            onValueChange = { onValueChange(it.toLong()) },
            valueRange = 50f..500f,
            steps = 45, // roughly every 10ms
            colors = SliderDefaults.colors(
                thumbColor = AgyPrimary,
                activeTrackColor = AgyPrimary,
                inactiveTrackColor = AgyPrimary.copy(alpha = 0.3f)
            )
        )
        Text(
            text = "Time to wait before capturing terminal screen output after commands complete.",
            style = MaterialTheme.typography.bodySmall,
            color = AgyTextSecondary
        )
    }
}
