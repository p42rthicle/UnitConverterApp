import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import me.darthwithap.android.unitconverterapp.R
import me.darthwithap.android.unitconverterapp.presentation.conversion.BatchConversionScreen
import me.darthwithap.android.unitconverterapp.presentation.conversion.ConversionEvent
import me.darthwithap.android.unitconverterapp.presentation.conversion.ConversionState
import me.darthwithap.android.unitconverterapp.presentation.conversion.SimpleConversionScreenState
import me.darthwithap.android.unitconverterapp.presentation.conversion.components.HeaderBar
import me.darthwithap.android.unitconverterapp.util.ConversionError

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ConversionScreen(
    state: ConversionState,
    onEvent: (ConversionEvent) -> Unit,
    onHistoryIconClick: () -> Unit,
    onFavouriteIconClick: () -> Unit
) {
  val context = LocalContext.current
  
  LaunchedEffect(key1 = state.error) {
    val message: String? = when (state.error) {
      null -> null
      ConversionError.DB_ERROR -> context.getString(R.string.error_db)
      ConversionError.INVALID_INPUT_VALUE -> context.getString(R.string.error_invalid_input)
      ConversionError.DIFFERENT_COLLECTIONS -> context.getString(R.string.error_same_units)
      ConversionError.GENERAL_ERROR -> context.getString(R.string.error_general)
      ConversionError.INVALID_CHARACTERS_FOUND -> context.getString(R.string.invalid_chracters_found)
      ConversionError.INVALID_DECIMAL_POSITION -> context.getString(R.string.invalid_decimal_position)
      ConversionError.ONLY_DECIMAL_INVALID -> context.getString(R.string.only_decimal_invalid)
      ConversionError.NEGATIVE_VALUES_NOT_ALLOWED -> context.getString(R.string.negative_values_not_allowed)
    }
    message?.let {
      Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
      onEvent(ConversionEvent.OnErrorSeen)
    }
  }
  
  Scaffold(topBar = {
    state.currentCollection?.let {
      HeaderBar(
          collections = state.collections,
          currentCollection = it,
          isCollectionDropDownOpen = state.isChoosingCollection,
          isBatchConversion = state.isBatchConversion,
          showMenu = state.shouldShowOptionsMenu,
          onBatchIconClick = { onEvent(ConversionEvent.ToggleBatchConversion) },
          onHistoryIconClick = onHistoryIconClick,
          onFavouriteIconClick = onFavouriteIconClick,
          onCollectionClick = { onEvent(ConversionEvent.ChoosingCollection) },
          onDropDownDismiss = { onEvent(ConversionEvent.StoppedChoosingCollection) },
          onHideOptionsMenu = { onEvent(ConversionEvent.HideOptionsMenu) },
          onShowOptionsMenu = { onEvent(ConversionEvent.ShowOptionsMenu) },
          onAboutUsClick = {
            Toast.makeText(context, "About us clicked", Toast.LENGTH_SHORT).show()
            onEvent(ConversionEvent.HideOptionsMenu)
          }
      ) { chosenCollection ->
        onEvent(ConversionEvent.ChosenCollection(chosenCollection))
      }
    }
  }) { paddingValues ->
    Column(modifier = Modifier
        .fillMaxSize()
        .background(MaterialTheme.colorScheme.surface)
        .padding(paddingValues)
        .padding(16.dp)) {
      if (state.isBatchConversion) {
        BatchConversionScreen(state = state, onEvent = onEvent)
      } else {
        SimpleConversionScreenState(state = state, onEvent = onEvent)
      }
    }
  }
}
