package com.meronacompany.design.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import com.google.accompanist.systemuicontroller.rememberSystemUiController

private val DarkColorScheme = darkColorScheme(
    primary = PrimaryDarkColor,
    onPrimary = OnPrimaryDarkColor,
    secondary = SecondaryDarkColor,
    tertiary = TertiaryDarkColor,
    background = BackgroundDarkColor,
    // 카테고리: surfaceVariant, outlineVariant
    surfaceVariant = surfaceVariantDarkColor, // 카테고리 인기
    outlineVariant = outLineVariantDarkColor // 카테고리 BEST
)

private val LightColorScheme = lightColorScheme(
    primary = PrimaryLightColor,
    secondary = PurpleGrey40,
    tertiary = Pink40

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
fun BAB_FLIXTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicDarkColorScheme(context)
        }
        darkTheme -> DarkColorScheme
        else -> DarkColorScheme // 일단 모두 dark로 설정
    }

    val systemUiController = rememberSystemUiController()
    val statusBarColor = colorScheme.primary
    val navigationBarColor = colorScheme.background

    SideEffect {
        systemUiController.setStatusBarColor(statusBarColor)
        systemUiController.setNavigationBarColor(navigationBarColor)
    }

    MaterialTheme(
        colorScheme = colorScheme,
        content = content
    )
}

object BAB_FLIXTheme {
    val typography: BobFlixTypography
        @Composable
        get() = LocalBabFlixTypography.current
}