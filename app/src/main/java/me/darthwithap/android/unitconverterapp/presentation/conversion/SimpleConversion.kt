package me.darthwithap.android.unitconverterapp.presentation.conversion

import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.capitalize
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.intl.Locale
import androidx.compose.ui.unit.dp
import me.darthwithap.android.unitconverterapp.R
import me.darthwithap.android.unitconverterapp.presentation.conversion.components.ConversionItem
import me.darthwithap.android.unitconverterapp.presentation.conversion.components.ConversionOptionsRow
import me.darthwithap.android.unitconverterapp.presentation.conversion.components.ConversionUnitsItem
import me.darthwithap.android.unitconverterapp.presentation.conversion.components.UnitDisplaySelector

@Composable
fun SimpleConversionScreenState(
    state: ConversionState,
    onEvent: (ConversionEvent) -> Unit
) {
  val context = LocalContext.current
  val clipboardManager = LocalClipboardManager.current
  
  Box(
      modifier = Modifier.fillMaxSize()
  ) {
    Column {
      state.fromUnit?.let { fromUnit ->
        UnitDisplaySelector(
            modifier = Modifier.fillMaxWidth(),
            uiCollection = state.currentCollection,
            singleUnit = fromUnit,
            value = state.inputValue,
            isEditable = true,
            isDropDown = true,
            hasMoreMenu = false,
            onInputValueChanged = { onEvent(ConversionEvent.InputValueChanged(it)) },
            onConvert = {
              onEvent(ConversionEvent.Convert(true))
            },
            isDropDownOpen = state.isChoosingFromUnit,
            onUnitDropDownClick = { onEvent(ConversionEvent.ChoosingFromUnit) },
            onDropDownDismiss = { onEvent(ConversionEvent.StoppedChoosingFromUnit) }
        ) {
          onEvent(ConversionEvent.ChosenFromUnit(it))
        }
      }
      Spacer(modifier = Modifier.height(4.dp))
      state.toUnit?.let { toUnit ->
        UnitDisplaySelector(
            modifier = Modifier.fillMaxWidth(),
            uiCollection = state.currentCollection,
            singleUnit = toUnit,
            value = state.outputValue ?: "",
            isEditable = false,
            isDropDown = true,
            hasMoreMenu = false,
            onInputValueChanged = {},
            onConvert = {},
            isDropDownOpen = state.isChoosingToUnit,
            onUnitDropDownClick = { onEvent(ConversionEvent.ChoosingToUnit) },
            onDropDownDismiss = { onEvent(ConversionEvent.StoppedChoosingToUnit) }
        ) {
          onEvent(ConversionEvent.ChosenToUnit(it))
        }
      }
      Spacer(modifier = Modifier.height(16.dp))
      ConversionOptionsRow(
          onSwapUnitsClick = {
            onEvent(ConversionEvent.SwapUnits)
          },
          onInfoClick = {
            onEvent(ConversionEvent.ShowFormulaInfoDialog)
          },
          onCopyConversionClick = {
            clipboardManager.setText(buildAnnotatedString {
              append("${state.inputValue} ${state.fromUnit?.name?.capitalize(Locale.current)}")
              append(" = ")
              append("${state.outputValue} ${state.toUnit?.name?.capitalize(Locale.current)}")
            })
            Toast.makeText(
                context, context.getString(R.string.copied_to_clipboard), Toast.LENGTH_SHORT
            ).show()
          }
      )
      if (state.recentConversions.isNotEmpty()) {
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            modifier = Modifier.padding(vertical = 8.dp),
            text = stringResource(id = R.string.last_conversion),
            style = MaterialTheme.typography.bodyLarge.copy(
                fontWeight = FontWeight.SemiBold,
                color = MaterialTheme.colorScheme.onSurface
            )
        )
        Spacer(modifier = Modifier.height(16.dp))
        ConversionItem(conversion = state.recentConversions.first(), onFavouriteClick = {
          onEvent(ConversionEvent.ToggleFavouriteConversion(it))
        }, onClick = {})
      }
      if (state.recentConversionUnits.isNotEmpty()) {
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            modifier = Modifier.padding(vertical = 8.dp),
            text = stringResource(id = R.string.last_conversion_units),
            style = MaterialTheme.typography.bodyLarge.copy(
                fontWeight = FontWeight.SemiBold,
                color = MaterialTheme.colorScheme.onSurface
            )
        )
        Spacer(modifier = Modifier.height(16.dp))
        ConversionUnitsItem(units = state.recentConversionUnits.first(), onFavouriteClick = {
          onEvent(ConversionEvent.ToggleFavouriteConversionUnits(it))
        }, onClick = {})
      }
    }
    if (state.showInfoDialog && state.fromUnit != null && state.toUnit != null) {
      val resource = getConversionStringResource(state.fromUnit.name, state.toUnit.name)
      val displayText = if (resource != null) {
        stringResource(id = resource)
      } else {
        val toValue = ((1.0 - state.fromUnit.offset) / state.fromUnit.multiplier) * state.toUnit.multiplier + state.toUnit.offset
        "1.0 ${state.fromUnit.name.capitalize(Locale.current)} = $toValue ${state.toUnit.name.capitalize(Locale.current)}"
      }
      AlertDialog(
          onDismissRequest = { onEvent(ConversionEvent.HideFormulaInfoDialog) },
          title = { Text(text = "Conversion Formula") },
          text = {
            Text(displayText)
          },
          confirmButton = {
            Text(text = "Got it", modifier = Modifier.clickable { onEvent(ConversionEvent.HideFormulaInfoDialog) })
          }
      )
    }
  }
}

@Composable
private fun getConversionStringResource(fromUnit: String, toUnit: String): Int? {
  val conversionMap = mapOf(
      "fahrenheit" to "celsius" to R.string.fahrenheit_to_celsius,
      "fahrenheit" to "kelvin" to R.string.fahrenheit_to_kelvin,
      "celsius" to "kelvin" to R.string.celsius_to_kelvin,
      "celsius" to "fahrenheit" to R.string.celsius_to_fahrenheit,
      "kelvin" to "fahrenheit" to R.string.kelvin_to_fahrenheit,
      "kelvin" to "celsius" to R.string.kelvin_to_celsius
  )
  
  return conversionMap[fromUnit to toUnit]
}