package com.wreckingball.wordlier.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.staticCompositionLocalOf

private val DarkColorPalette = darkColorScheme(
        primary = Purple200,
        secondary = Purple700,
        tertiary = Teal200,
)

private val LightColorPalette = lightColorScheme(
        primary = Purple500,
        secondary = Purple700,
        tertiary = Teal200,

        /* Other default colors to override
    background = Color.White,
    surface = Color.White,
    onPrimary = Color.White,
    onSecondary = Color.Black,
    onBackground = Color.Black,
    onSurface = Color.Black,
    */
)

val LocalDimensions = staticCompositionLocalOf { Dimensions() }

val MaterialTheme.dimensions
    @Composable
    @ReadOnlyComposable
    get() = LocalDimensions.current


@Composable
fun WordlierTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit,
) {
    val colors = if (darkTheme) {
        DarkColorPalette
    } else {
        LightColorPalette
    }

    CompositionLocalProvider(LocalDimensions provides Dimensions()) {
        MaterialTheme(
            colorScheme = colors,
            typography = Typography,
            shapes = Shapes,
            content = content,
        )
    }
}