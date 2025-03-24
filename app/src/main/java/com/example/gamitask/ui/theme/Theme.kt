package com.example.gamitask.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext

private val DarkColorScheme = darkColorScheme(
    primary = Color(0xFF1E88E5),  // Vibrant Blue
    onPrimary = Color.White,
    secondary = Color(0xFF64B5F6), // Light Blue
    onSecondary = Color.Black,
    tertiary = Color(0xFF90CAF9),  // Soft Blue Accent
    background = Color(0xFF121212), // Dark Grey
    onBackground = Color(0xFFE0E0E0), // Light Grey
    surface = Color(0xFF1E1E1E),  // Almost Black
    onSurface = Color(0xFFFAFAFA),  // Almost White
)

private val LightColorScheme = lightColorScheme(
    primary = Color(0xFF1976D2),  // Rich Blue
    onPrimary = Color.White,
    secondary = Color(0xFFBBDEFB), // Soft Blue
    onSecondary = Color.Black,
    tertiary = Color(0xFF42A5F5),  // Bright Blue Accent
    background = Color(0xFFF5F5F5), // Cool Light Grey
    onBackground = Color(0xFF212121), // Dark Grey
    surface = Color(0xFFFFFFFF),  // Pure White
    onSurface = Color(0xFF212121),  // Dark Grey
)

@Composable
fun GamiTaskTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = true,
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

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
