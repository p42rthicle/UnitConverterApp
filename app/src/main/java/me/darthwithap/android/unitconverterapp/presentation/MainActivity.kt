package me.darthwithap.android.unitconverterapp.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import me.darthwithap.android.unitconverterapp.presentation.ui.theme.UnitConverterAppTheme

class MainActivity : ComponentActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContent {
      UnitConverterAppTheme {
      }
    }
  }
}

