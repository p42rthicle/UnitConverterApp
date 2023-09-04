import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
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
import me.darthwithap.android.unitconverterapp.presentation.conversion.ConversionEvent
import me.darthwithap.android.unitconverterapp.presentation.conversion.ConversionState
import me.darthwithap.android.unitconverterapp.presentation.conversion.components.HeaderBar
import me.darthwithap.android.unitconverterapp.presentation.conversion.components.UnitDisplaySelector
import me.darthwithap.android.unitconverterapp.util.ConversionError

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ConversionScreen(
    state: ConversionState,
    onEvent: (ConversionEvent) -> Unit
) {
  val context = LocalContext.current
  
  LaunchedEffect(key1 = state.error) {
    val message: String? = when (state.error) {
      null -> null
      ConversionError.DB_ERROR -> context.getString(R.string.error_db)
      ConversionError.INVALID_INPUT_VALUE -> context.getString(R.string.error_invalid_input)
      ConversionError.DIFFERENT_COLLECTIONS -> context.getString(R.string.error_same_units)
      ConversionError.GENERAL_ERROR -> context.getString(R.string.error_general)
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
          onCollectionClick = { onEvent(ConversionEvent.ChoosingCollection) },
          onDropDownDismiss = { onEvent(ConversionEvent.StoppedChoosingCollection) },
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
      
    }
  }
}