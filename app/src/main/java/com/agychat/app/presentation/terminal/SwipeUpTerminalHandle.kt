package com.agychat.app.presentation.terminal

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Icon
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp

private val SwipeZoneBg = Color(0xFF0A0E14)
private val HandleGlow = Color(0xFF00FF9C)

/**
 * Invisible swipe-up zone anchored at the bottom of the screen.
 * When the user swipes upward in this region, [onSwipeUp] is triggered.
 */
@Composable
fun SwipeUpTerminalHandle(
    onSwipeUp: () -> Unit,
    modifier: Modifier = Modifier
) {
    var cumulativeDrag by remember { mutableFloatStateOf(0f) }

    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(28.dp)
            .pointerInput(Unit) {
                detectDragGestures(
                    onDragStart = { cumulativeDrag = 0f },
                    onDragEnd = {
                        // Threshold: if user dragged up more than 40px
                        if (cumulativeDrag < -40f) {
                            onSwipeUp()
                        }
                        cumulativeDrag = 0f
                    },
                    onDragCancel = { cumulativeDrag = 0f },
                    onDrag = { change, dragAmount ->
                        change.consume()
                        cumulativeDrag += dragAmount.y
                    }
                )
            },
        contentAlignment = Alignment.Center
    ) {
        // Subtle visual indicator — a glowing thin bar at the bottom
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                imageVector = Icons.Default.KeyboardArrowUp,
                contentDescription = "Swipe up for terminal",
                tint = HandleGlow.copy(alpha = 0.35f),
                modifier = Modifier.size(16.dp)
            )
            Spacer(modifier = Modifier.height(2.dp))
            Box(
                modifier = Modifier
                    .width(48.dp)
                    .height(3.dp)
                    .clip(RoundedCornerShape(1.5.dp))
                    .background(
                        Brush.horizontalGradient(
                            colors = listOf(
                                Color.Transparent,
                                HandleGlow.copy(alpha = 0.3f),
                                Color.Transparent
                            )
                        )
                    )
            )
        }
    }
}
