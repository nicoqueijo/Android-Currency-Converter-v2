package com.nicoqueijo.android.ui

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

@Composable
fun AndroidCurrencyConverterTheme(
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

private val DarkColorScheme = darkColorScheme(
    surface = Black,
    primary = White,
    secondary = Black,
    tertiary = DarkGray,
    tertiaryContainer = LightGray,
    onTertiary = Gray,
    onSurface = AlmostBlack,
    inverseOnSurface = AlmostWhite,
    surfaceVariant = SlightlyLighterAlmostBlack,
)

private val LightColorScheme = lightColorScheme(
    surface = White,
    primary = Black,
    secondary = White,
    tertiary = LightGray,
    tertiaryContainer = DarkGray,
    onTertiary = Gray,
    onSurface = AlmostWhite,
    inverseOnSurface = AlmostBlack,
    surfaceVariant = SlightlyDarkerAlmostWhite,
)