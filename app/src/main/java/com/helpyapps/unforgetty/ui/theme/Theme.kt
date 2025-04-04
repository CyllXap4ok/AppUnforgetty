package com.helpyapps.unforgetty.ui.theme

import android.app.Activity
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.ripple.RippleAlpha
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LocalRippleConfiguration
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RippleConfiguration
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

private val LightColorScheme = lightColorScheme(
    primary = LightColorPalette.contentColor,
    onPrimary = LightColorPalette.containerColor,
    secondary = LightColorPalette.hintColor,
    background = LightColorPalette.containerColor,
    onBackground = LightColorPalette.contentColor,
    surface = LightColorPalette.containerColor,
    outlineVariant = LightColorPalette.contentColor
)

private val DarkColorScheme = darkColorScheme(
    primary = DarkColorPalette.contentColor,
    onPrimary = DarkColorPalette.containerColor,
    secondary = DarkColorPalette.hintColor,
    background = DarkColorPalette.containerColor,
    onBackground = DarkColorPalette.contentColor,
    surface = DarkColorPalette.containerColor,
    outlineVariant = DarkColorPalette.contentColor
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UnforgettyTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }
    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = colorScheme.onPrimary.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = !darkTheme
        }
    }

    val rippleConfiguration = RippleConfiguration(
        color = colorScheme.primary,
        rippleAlpha = RippleAlpha(0.2f, 0.2f, 0.2f, 0.2f)
    )

    CompositionLocalProvider(
        LocalRippleConfiguration provides rippleConfiguration
    ) {
        MaterialTheme(
            colorScheme = colorScheme,
            typography = Typography,
            content = content
        )
    }
}