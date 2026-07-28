package com.agychat.app.presentation.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

private val DarkColorScheme = darkColorScheme(
    primary = AgyPrimary,
    onPrimary = AgyOnPrimary,
    primaryContainer = AgyPrimaryVariant,
    secondary = AgySecondary,
    onSecondary = AgyOnSecondary,
    secondaryContainer = AgySecondaryVariant,
    background = AgyBackgroundDark,
    onBackground = AgyOnBackgroundDark,
    surface = AgySurfaceDark,
    onSurface = AgyOnSurfaceDark,
    surfaceVariant = AgySurfaceElevatedDark,
    error = AgyError,
    onError = AgyOnError
)

private val LightColorScheme = lightColorScheme(
    primary = AgyPrimary,
    onPrimary = AgyOnPrimary,
    primaryContainer = AgyPrimaryVariant,
    secondary = AgySecondary,
    onSecondary = AgyOnSecondary,
    secondaryContainer = AgySecondaryVariant,
    background = AgyBackgroundLight,
    onBackground = AgyOnBackgroundLight,
    surface = AgySurfaceLight,
    onSurface = AgyOnSurfaceLight,
    surfaceVariant = AgySurfaceElevatedLight,
    error = AgyError,
    onError = AgyOnError
)

@Composable
fun AgyChatTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = false, // Disabled: we want our custom brand colors
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }
        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as? android.app.Activity)?.window ?: return@SideEffect
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = !darkTheme
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = AgyChatTypography,
        shapes = AgyChatShapes,
        content = content
    )
}
