package com.agychat.app.presentation.common

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.agychat.app.presentation.theme.Dimens
import com.agychat.app.presentation.theme.StatusConnected
import com.agychat.app.presentation.theme.StatusConnecting
import com.agychat.app.presentation.theme.StatusDisconnected
import com.agychat.app.presentation.theme.AgySurfaceDark
import com.agychat.app.presentation.theme.AgyTextPrimary

enum class PtyConnectionState {
    CONNECTED, CONNECTING, DISCONNECTED
}

@Composable
fun ConnectionStatusBadge(
    state: PtyConnectionState,
    modifier: Modifier = Modifier
) {
    val statusColor = when (state) {
        PtyConnectionState.CONNECTED -> StatusConnected
        PtyConnectionState.CONNECTING -> StatusConnecting
        PtyConnectionState.DISCONNECTED -> StatusDisconnected
    }
    
    val text = when (state) {
        PtyConnectionState.CONNECTED -> "Connected"
        PtyConnectionState.CONNECTING -> "Connecting..."
        PtyConnectionState.DISCONNECTED -> "Disconnected"
    }

    val infiniteTransition = rememberInfiniteTransition()
    val alpha by infiniteTransition.animateFloat(
        initialValue = 1f,
        targetValue = if (state == PtyConnectionState.CONNECTING) 0.3f else 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(1000, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ), label = "pulse"
    )

    Row(
        modifier = modifier
            .clip(RoundedCornerShape(4.dp))
            .background(AgySurfaceDark)
            .padding(horizontal = Dimens.SpacingSm, vertical = 4.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(8.dp)
                .alpha(alpha)
                .clip(CircleShape)
                .background(statusColor)
        )
        Spacer(modifier = Modifier.width(Dimens.SpacingSm))
        Text(
            text = text,
            color = AgyTextPrimary,
            style = MaterialTheme.typography.labelSmall
        )
    }
}
