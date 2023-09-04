package me.darthwithap.android.unitconverterapp.presentation.ui.theme

import android.app.Activity
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat


private val darkColorPalette = darkColorScheme(
    primary = PrimaryDark,
    secondary = SecondaryDark,
    tertiary = TertiaryDark,
    background = DarkGrey,
    onPrimary = TextBlackOnWhite,
    onBackground = Color.White,
    surface = DarkerGrey,
    onSurface = Color.White,
    surfaceVariant = DarkGreyVariant
)

private val lightColorPalette = lightColorScheme(
    primary = PrimaryLight,
    secondary = SecondaryLight,
    tertiary = TertiaryLight,
    background = LightBlueGrey,
    onPrimary = LightGrey,
    onBackground = TextBlackOnWhite,
    surface = Color.White,
    onSurface = TextBlackOnWhite,
    surfaceVariant = LightBlueGreyVariant
)

@Composable
fun UnitConverterAppTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
  val colorScheme = if (darkTheme) {
    darkColorPalette
  } else {
    lightColorPalette
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