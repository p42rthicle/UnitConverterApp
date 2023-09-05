package me.darthwithap.android.unitconverterapp.presentation

import ConversionScreen
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import me.darthwithap.android.unitconverterapp.presentation.conversion.ConversionEvent
import me.darthwithap.android.unitconverterapp.presentation.conversion.ConversionViewModel
import me.darthwithap.android.unitconverterapp.presentation.conversion.FavouriteScreen
import me.darthwithap.android.unitconverterapp.presentation.conversion.HistoryScreen
import me.darthwithap.android.unitconverterapp.presentation.ui.theme.UnitConverterAppTheme
import me.darthwithap.android.unitconverterapp.util.Routes

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
          val navController = rememberNavController()
          NavHost(navController = navController, startDestination = Routes.ConversionScreen) {
            composable(Routes.ConversionScreen) {
              ConversionScreen(
                  state = state,
                  onEvent = viewModel::onEvent,
                  onHistoryIconClick = { navController.navigate(Routes.HistoryScreen) },
                  onFavouriteIconClick = { navController.navigate(Routes.FavouriteScreen) }
              )
            }
            composable(Routes.HistoryScreen) {
              HistoryScreen(
                  state = state,
                  onConversionClick = {
                    viewModel.onEvent(ConversionEvent.ChosenConversion(it))
                    navController.popBackStack()
                  },
                  onConversionFavouriteClick = {
                    viewModel.onEvent(ConversionEvent.ToggleFavouriteConversion(it))
                  },
                  onConversionUnitsClick = {
                    viewModel.onEvent(ConversionEvent.ChosenConversionUnits(it))
                    navController.popBackStack()
                  },
                  onConversionUnitsFavouriteClick = {
                    viewModel.onEvent(ConversionEvent.ToggleFavouriteConversionUnits(it))
                  }
              )
            }
            composable(Routes.FavouriteScreen) {
              FavouriteScreen()
            }
          }
        }
      }
    }
  }
}

