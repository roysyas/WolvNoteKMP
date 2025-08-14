package com.roys.wolvnotekmp.presentation.util.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

private val LightColorScheme = lightColorScheme(
    primary = Green35,
    secondary = White,
    error = Red40,
    outline = Green40,
    tertiary = Transparent,
    primaryContainer = White,
    outlineVariant = Grey60
)

private val DarkColorScheme = darkColorScheme(
    primary = Green45,
    secondary = White,
    error = Red80,
    outline = Green50,
    tertiary = Transparent,
    primaryContainer = Black20,
    outlineVariant = Grey90
)

@Composable
fun WolvNoteTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}