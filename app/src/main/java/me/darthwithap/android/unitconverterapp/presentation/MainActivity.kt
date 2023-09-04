package me.darthwithap.android.unitconverterapp.presentation

import ConversionScreen
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import dagger.hilt.android.AndroidEntryPoint
import me.darthwithap.android.unitconverterapp.presentation.conversion.ConversionViewModel
import me.darthwithap.android.unitconverterapp.presentation.ui.theme.UnitConverterAppTheme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContent {
      UnitConverterAppTheme {
        val viewModel: ConversionViewModel = hiltViewModel()
        val state = viewModel.state
        Surface(
            modifier = Modifier.fillMaxSize()
        ) {
          ConversionScreen(state = state, onEvent = viewModel::onEvent)
        }
      }
    }
  }
}

