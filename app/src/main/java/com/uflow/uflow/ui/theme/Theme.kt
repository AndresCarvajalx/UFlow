package com.uflow.uflow.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat
import com.uflow.uflow.ui.theme.themes.GreenDarkColors
import com.uflow.uflow.ui.theme.themes.GreenLightColors
import com.uflow.uflow.ui.theme.themes.OrangeDarkColors
import com.uflow.uflow.ui.theme.themes.OrangeLightColors

private val md_theme_light_primary = Color(0xFF006878)
private val md_theme_light_onPrimary = Color(0xFFFFFFFF)
private val md_theme_light_primaryContainer = Color(0xFFA6EEFF)
private val md_theme_light_onPrimaryContainer = Color(0xFF001F25)
private val md_theme_light_secondary = Color(0xFF4B6268)
private val md_theme_light_onSecondary = Color(0xFFFFFFFF)
private val md_theme_light_secondaryContainer = Color(0xFFCDE7EE)
private val md_theme_light_onSecondaryContainer = Color(0xFF051F24)
private val md_theme_light_tertiary = Color(0xFF555D7E)
private val md_theme_light_onTertiary = Color(0xFFFFFFFF)
private val md_theme_light_tertiaryContainer = Color(0xFFDCE1FF)
private val md_theme_light_onTertiaryContainer = Color(0xFF111A37)
private val md_theme_light_error = Color(0xFFBA1A1A)
private val md_theme_light_errorContainer = Color(0xFFFFDAD6)
private val md_theme_light_onError = Color(0xFFFFFFFF)
private val md_theme_light_onErrorContainer = Color(0xFF410002)
private val md_theme_light_background = Color(0xFFFBFCFD)
private val md_theme_light_onBackground = Color(0xFF191C1D)
private val md_theme_light_surface = Color(0xFFFBFCFD)
private val md_theme_light_onSurface = Color(0xFF191C1D)
private val md_theme_light_surfaceVariant = Color(0xFFDBE4E7)
private val md_theme_light_onSurfaceVariant = Color(0xFF3F484B)
private val md_theme_light_outline = Color(0xFF6F797B)
private val md_theme_light_inverseOnSurface = Color(0xFFEFF1F2)
private val md_theme_light_inverseSurface = Color(0xFF2E3132)
private val md_theme_light_inversePrimary = Color(0xFF53D7F1)
private val md_theme_light_shadow = Color(0xFF000000)
private val md_theme_light_surfaceTint = Color(0xFF006878)
private val md_theme_light_outlineVariant = Color(0xFFBFC8CB)
private val md_theme_light_scrim = Color(0xFF000000)

private val md_theme_dark_primary = Color(0xFF88B3F7) /////////////////
private val md_theme_dark_onPrimary = Color(0xFFC4C6C5) /////////////////
private val md_theme_dark_primaryContainer = Color(0xFF004A77) /////////////////
private val md_theme_dark_onPrimaryContainer = Color(0xFFC2E6FE)
private val md_theme_dark_secondary = Color(0xFFB2CBD1)
private val md_theme_dark_onSecondary = Color(0xFFFF0724)
private val md_theme_dark_secondaryContainer = Color(0xFF004A77) /////////////////
private val md_theme_dark_onSecondaryContainer = Color(0xFFCDE7EE)
private val md_theme_dark_tertiary = Color(0xFFBDC5EB)
private val md_theme_dark_onTertiary = Color(0xFF272F4D)
private val md_theme_dark_tertiaryContainer = Color(0xFF3D4565)
private val md_theme_dark_onTertiaryContainer = Color(0xFFDCE1FF)
private val md_theme_dark_error = Color(0xFFE47373)
private val md_theme_dark_errorContainer = Color(0xFF93000A)
private val md_theme_dark_onError = Color(0xFF690005)
private val md_theme_dark_onErrorContainer = Color(0xFFFFDAD6)
private val md_theme_dark_background = Color(0xFF1F1F1F) /////////////////
private val md_theme_dark_onBackground = Color(0xFFE1E3E4)
private val md_theme_dark_surface = Color(0xFF2D2E32)
private val md_theme_dark_onSurface = Color(0xFFE1E3E4)
private val md_theme_dark_surfaceVariant = Color(0xFF2D2E32)
private val md_theme_dark_onSurfaceVariant = Color(0xFFBFC8CB)
private val md_theme_dark_outline = Color(0xFF899295)
private val md_theme_dark_inverseOnSurface = Color(0xFF191C1D)
private val md_theme_dark_inverseSurface = Color(0xFFE1E3E4)
private val md_theme_dark_inversePrimary = Color(0xFF620078)
private val md_theme_dark_shadow = Color(0xFF000000)
private val md_theme_dark_surfaceTint = Color(0xFF53D7F1)
private val md_theme_dark_outlineVariant = Color(0xFF3F484B)
private val md_theme_dark_scrim = Color(0xFF000000)

private val DarkColorScheme = darkColorScheme(
    primary = md_theme_dark_primary,
    onPrimary = md_theme_dark_onPrimary,
    primaryContainer = md_theme_dark_primaryContainer,
    onPrimaryContainer = md_theme_dark_onPrimaryContainer,
    secondary = md_theme_dark_secondary,
    onSecondary = md_theme_dark_onSecondary,
    secondaryContainer = md_theme_dark_secondaryContainer,
    onSecondaryContainer = md_theme_dark_onSecondaryContainer,
    tertiary = md_theme_dark_tertiary,
    onTertiary = md_theme_dark_onTertiary,
    tertiaryContainer = md_theme_dark_tertiaryContainer,
    onTertiaryContainer = md_theme_dark_onTertiaryContainer,
    error = md_theme_dark_error,
    errorContainer = md_theme_dark_errorContainer,
    onError = md_theme_dark_onError,
    onErrorContainer = md_theme_dark_onErrorContainer,
    background = md_theme_dark_background,
    onBackground = md_theme_dark_onBackground,
    surface = md_theme_dark_surface,
    onSurface = md_theme_dark_onSurface,
    surfaceVariant = md_theme_dark_surfaceVariant,
    onSurfaceVariant = md_theme_dark_onSurfaceVariant,
    outline = md_theme_dark_outline,
    inverseOnSurface = md_theme_dark_inverseOnSurface,
    inverseSurface = md_theme_dark_inverseSurface,
    inversePrimary = md_theme_dark_inversePrimary,
    surfaceTint = md_theme_dark_surfaceTint,
    outlineVariant = md_theme_dark_outlineVariant,
    scrim = md_theme_dark_scrim,
)

private val LightColorScheme = lightColorScheme(
    primary = md_theme_light_primary,
    onPrimary = md_theme_light_onPrimary,
    primaryContainer = md_theme_light_primaryContainer,
    onPrimaryContainer = md_theme_light_onPrimaryContainer,
    secondary = md_theme_light_secondary,
    onSecondary = md_theme_light_onSecondary,
    secondaryContainer = md_theme_light_secondaryContainer,
    onSecondaryContainer = md_theme_light_onSecondaryContainer,
    tertiary = md_theme_light_tertiary,
    onTertiary = md_theme_light_onTertiary,
    tertiaryContainer = md_theme_light_tertiaryContainer,
    onTertiaryContainer = md_theme_light_onTertiaryContainer,
    error = md_theme_light_error,
    errorContainer = md_theme_light_errorContainer,
    onError = md_theme_light_onError,
    onErrorContainer = md_theme_light_onErrorContainer,
    background = md_theme_light_background,
    onBackground = md_theme_light_onBackground,
    surface = md_theme_light_surface,
    onSurface = md_theme_light_onSurface,
    surfaceVariant = md_theme_light_surfaceVariant,
    onSurfaceVariant = md_theme_light_onSurfaceVariant,
    outline = md_theme_light_outline,
    inverseOnSurface = md_theme_light_inverseOnSurface,
    inverseSurface = md_theme_light_inverseSurface,
    inversePrimary = md_theme_light_inversePrimary,
    surfaceTint = md_theme_light_surfaceTint,
    outlineVariant = md_theme_light_outlineVariant,
    scrim = md_theme_light_scrim,
)

@Composable
fun UFlowTheme(
    darkTheme: Boolean = true,
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
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = darkTheme
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}