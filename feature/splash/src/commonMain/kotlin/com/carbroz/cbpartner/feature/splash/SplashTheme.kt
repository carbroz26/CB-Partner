package com.carbroz.cbpartner.feature.splash

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val CarBrozTeal = Color(0xFF0DB7B5)
private val CarBrozTealDark = Color(0xFF078F8D)
private val CarBrozInk = Color(0xFF17202A)
private val CarBrozText = Color(0xFF59636E)
private val CarBrozSurface = Color(0xFFF8FCFC)
private val CarBrozMist = Color(0xFFE8F8F7)

@Composable
internal fun CarBrozSplashTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = lightColorScheme(
            primary = CarBrozTeal,
            onPrimary = Color.White,
            primaryContainer = CarBrozMist,
            onPrimaryContainer = CarBrozTealDark,
            surface = CarBrozSurface,
            onSurface = CarBrozInk,
            onSurfaceVariant = CarBrozText,
            background = Color.White,
            onBackground = CarBrozInk,
        ),
        content = content,
    )
}
