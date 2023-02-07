package org.ethereumphone.lightnodestats.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val ethOSColorPaletteLight = lightColors(
    primary = PrimaryLight,
    primaryVariant = SecondaryLight,
    secondary = AccentLight,
    background = white,
    surface = white,
    onPrimary = black,
    onSecondary = black,
    onBackground = black,
    onSurface = black,

    )

private val ethOSColorPaletteDark = darkColors(
    primary = PrimaryDark,
    primaryVariant = SecondaryDark,
    secondary = AccentDark,
    background = black,
    surface = darkgray3,
    onPrimary = white,
    onSecondary = white,
    onBackground = white,
    onSurface = white,
)
/* Other default colors to override
    background = Color.White,
    surface = Color.White,
    onPrimary = Color.White,
    onSecondary = Color.Black,
    onBackground = Color.Black,
    onSurface = Color.Black,
    */
@Composable
fun ethOSTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable() () -> Unit
) {
    /*val colors = if (darkTheme) {
        DarkColorPalette
    } else {
        LightColorPalette
    }*/

    val colors = ethOSColorPaletteDark

    MaterialTheme(
        colors = colors,
        typography = ethOSTypography,
        shapes = Shapes,
        content = content
    )
}