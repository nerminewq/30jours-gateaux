package com.example.a30joursgteaux.ui.theme

import androidx.compose.material3.*
import androidx.compose.runtime.Composable

private val LightColors = lightColorScheme(
    primary = PinkDark,
    onPrimary = White,
    secondary = Pink,
    onSecondary = White,
    background = PinkLight,
    onBackground = Black,
    surface = White,
    onSurface = Black
)

private val DarkColors = darkColorScheme(
    primary = Pink,
    onPrimary = Black,
    secondary = PinkDark,
    onSecondary = White,
    background = Black,
    onBackground = PinkLight,
    surface = PinkDark,
    onSurface = White
)

@Composable
fun _30JoursGteauxTheme(
    darkTheme: Boolean = false,
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = if (darkTheme) DarkColors else LightColors,
        typography = Typography,
        content = content
    )
}
