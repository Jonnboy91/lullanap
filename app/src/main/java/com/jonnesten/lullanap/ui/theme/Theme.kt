package com.jonnesten.lullanap.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable

private val DarkColorPalette = darkColors(
    primary = LightBlack,
    onPrimary = White,
    secondary = Pink,
    secondaryVariant = DarkPink
)

private val LightColorPalette = lightColors(
    primary = White,
    onPrimary = Black,
    secondary = Pink,
    secondaryVariant = DarkPink
)


@Composable
fun LullaNapTheme(darkTheme: Boolean, content: @Composable () -> Unit) {

    val colors = if (darkTheme) {
        DarkColorPalette
    } else {
        LightColorPalette
    }

    MaterialTheme(
        colors = colors,
        typography = typography,
        shapes = Shapes,
        content = content
    )
}