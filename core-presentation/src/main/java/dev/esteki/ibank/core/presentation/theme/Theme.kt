package dev.esteki.ibank.core.presentation.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.unit.LayoutDirection

/**
 * NOTE: These schemes are built directly from the provided brand swatches,
 * NOT from a generated M3 tonal palette. Contrast ratios below are eyeballed
 * against WCAG-ish targets but you should verify with the Material Theme
 * Builder or a contrast checker before shipping, especially:
 *  - onPrimaryContainer on Primary40 (#A8A3D7)
 *  - onSurfaceVariant on Neutral20 (#E0E0E0)
 */

private val LightColorScheme = lightColorScheme(
    primary = Primary90,
    onPrimary = NeutralWhite,
    primaryContainer = Primary40,
    onPrimaryContainer = Primary90,

    secondary = Primary70,
    onSecondary = NeutralWhite,
    secondaryContainer = Primary10,
    onSecondaryContainer = Primary90,

    tertiary = SemanticAccent,
    onTertiary = NeutralWhite,
    tertiaryContainer = SemanticWarning,
    onTertiaryContainer = Neutral90,

    error = SemanticError,
    onError = NeutralWhite,
    errorContainer = Color(0xFFFFD9DF),
    onErrorContainer = SemanticError,

    background = NeutralWhite,
    onBackground = Neutral90,

    surface = NeutralWhite,
    onSurface = Neutral90,
    surfaceVariant = Neutral20,
    onSurfaceVariant = Neutral60a,

    outline = Neutral60b,
    outlineVariant = Neutral30,

    inverseSurface = Neutral90,
    inverseOnSurface = NeutralWhite,
    inversePrimary = Primary40,
)

private val DarkColorScheme = darkColorScheme(
    primary = Primary40,
    onPrimary = Primary90,
    primaryContainer = Primary70,
    onPrimaryContainer = Primary10,

    secondary = Primary70,
    onSecondary = NeutralWhite,
    secondaryContainer = Color(0xFF1A1356),
    onSecondaryContainer = Primary40,

    tertiary = SemanticWarning,
    onTertiary = Neutral90,
    tertiaryContainer = SemanticAccent,
    onTertiaryContainer = NeutralWhite,

    error = SemanticError,
    onError = Neutral90,
    errorContainer = Color(0xFF7A1B30),
    onErrorContainer = Color(0xFFFFD9DF),

    background = Neutral90,
    onBackground = NeutralWhite,

    surface = Color(0xFF1F1F1F),
    onSurface = NeutralWhite,
    surfaceVariant = Color(0xFF474747),
    onSurfaceVariant = Neutral30,

    outline = Neutral60b,
    outlineVariant = Color(0xFF474747),

    inverseSurface = NeutralWhite,
    inverseOnSurface = Neutral90,
    inversePrimary = Primary90,
)


@Composable
fun IBankTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = false,
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

    CompositionLocalProvider(
        LocalLayoutDirection provides LayoutDirection.Ltr,
        LocalTypography provides AppTypography,
    ) {
        MaterialTheme(
            colorScheme = colorScheme,
            typography = Typography,
            content = content
        )
    }
}
