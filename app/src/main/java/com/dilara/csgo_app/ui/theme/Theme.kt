package com.dilara.csgo_app.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

private val DarkColorScheme = darkColorScheme(
    primary = CsgoOrange,
    secondary = CsgoGold,
    tertiary = CsgoBlue,
    background = CsgoDarkBlue,
    surface = CsgoNavy,
    onPrimary = CsgoWhite,
    onSecondary = CsgoBlack,
    onTertiary = CsgoWhite,
    onBackground = CsgoWhite,
    onSurface = CsgoWhite,
    surfaceVariant = CsgoLightGray,
    onSurfaceVariant = CsgoWhite,
    outline = CsgoOrange,
    outlineVariant = CsgoGray
)

private val LightColorScheme = lightColorScheme(
    primary = CsgoOrange,
    secondary = CsgoGold,
    tertiary = CsgoBlue,
    background = CsgoWhite,
    surface = CsgoLightGray,
    onPrimary = CsgoWhite,
    onSecondary = CsgoBlack,
    onTertiary = CsgoWhite,
    onBackground = CsgoBlack,
    onSurface = CsgoBlack,
    surfaceVariant = CsgoGray,
    onSurfaceVariant = CsgoBlack,
    outline = CsgoOrange,
    outlineVariant = CsgoGray
)

@Composable
fun CsgoAppTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
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
    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = colorScheme.primary.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = !darkTheme
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}