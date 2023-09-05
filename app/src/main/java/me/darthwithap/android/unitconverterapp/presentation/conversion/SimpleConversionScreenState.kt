package me.darthwithap.android.unitconverterapp.presentation.conversion

import android.widget.Toast
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.capitalize
import androidx.compose.ui.text.intl.Locale
import androidx.compose.ui.unit.dp
import me.darthwithap.android.unitconverterapp.R
import me.darthwithap.android.unitconverterapp.presentation.conversion.components.ConversionOptionsRow
import me.darthwithap.android.unitconverterapp.presentation.conversion.components.UnitDisplaySelector

@Composable
fun SimpleConversionScreenState(
    state: ConversionState,
    onEvent: (ConversionEvent) -> Unit
) {
  val context = LocalContext.current
  val clipboardManager = LocalClipboardManager.current
  
  
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
}