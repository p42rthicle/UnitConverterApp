package me.darthwithap.android.unitconverterapp.presentation.conversion

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import me.darthwithap.android.unitconverterapp.presentation.conversion.components.UnitDisplaySelector

@Composable
fun SimpleConversionScreenState(
    state: ConversionState,
    onEvent: (ConversionEvent) -> Unit
) {
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
          onEvent(ConversionEvent.Convert(state.inputValue, state.fromUnit, state.toUnit
              ?: state.currentCollection?.collection?.units?.last()
              ?: return@UnitDisplaySelector
          ))
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
}