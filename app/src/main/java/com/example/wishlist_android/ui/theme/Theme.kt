package com.example.wishlist_android.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext

private val DarkColorScheme = darkColorScheme(
    primary = PrimaryBlue,

    primaryContainer = LightGrey,
    onPrimaryContainer = DarkWhite,
    onPrimary = DarkWhite,

    secondary = PurpleGrey80,
    tertiary = White,

    background = DarkGrey,
    onBackground = DarkWhite,

    error = ErrorColor,
    errorContainer = ErrorColorContainer,
    onErrorContainer = DarkWhite,


    )

private val LightColorScheme = lightColorScheme(
    primary = Blue,

    primaryContainer = DarkWhite,
    onPrimaryContainer = DarkGrey,
    onPrimary = DarkWhite,

    secondary = PurpleGrey40,
    tertiary = Grey,

    background = White,
    onBackground = LightGrey,

    error = ErrorColor,
    errorContainer = ErrorColorContainer,
    onErrorContainer = DarkWhite,


    /* Other default colors to override
background = Color(0xFFFFFBFE),
surface = Color(0xFFFFFBFE),
onPrimary = Color.White,
onSecondary = Color.White,
onTertiary = Color.White,
onBackground = Color(0xFF1C1B1F),
onSurface = Color(0xFF1C1B1F),
*/
)

@Composable
fun WishlistandroidTheme(
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

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}