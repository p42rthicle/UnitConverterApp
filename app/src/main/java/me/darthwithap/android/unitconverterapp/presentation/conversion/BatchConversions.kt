package me.darthwithap.android.unitconverterapp.presentation.conversion

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import me.darthwithap.android.unitconverterapp.presentation.conversion.components.UnitDisplaySelector

@Composable
fun BatchConversionScreen(
    modifier: Modifier = Modifier,
    state: ConversionState,
    onEvent: (ConversionEvent) -> Unit
) {
  state.fromUnit?.let { fromUnit ->
    UnitDisplaySelector(
        modifier = modifier.fillMaxWidth(),
        uiCollection = state.currentCollection,
        singleUnit = fromUnit,
        value = state.inputValue,
        isEditable = true,
        isDropDown = true,
        hasMoreMenu = true,
        onInputValueChanged = { onEvent(ConversionEvent.InputValueChanged(it, true)) },
        onConvert = {
          onEvent(ConversionEvent.PerformBatchConversion(state.inputValue))
        },
        isDropDownOpen = state.isChoosingFromUnit,
        onUnitDropDownClick = { onEvent(ConversionEvent.ChoosingFromUnit) },
        onDropDownDismiss = { onEvent(ConversionEvent.StoppedChoosingFromUnit) }
    ) {
      onEvent(ConversionEvent.ChosenFromUnit(it, true))
    }
  }
  Spacer(modifier = Modifier.height(12.dp))
  LazyColumn {
    items(state.currentCollection?.collection?.units ?: return@LazyColumn) { unit ->
      Spacer(modifier = Modifier.height(4.dp))
      UnitDisplaySelector(
          modifier = Modifier.fillMaxWidth(),
          uiCollection = state.currentCollection,
          singleUnit = unit,
          value = state.batchConversionResult[unit] ?: "",
          isEditable = false,
          isDropDown = false,
          hasMoreMenu = true,
          onMoreMenuClick = {},
          onInputValueChanged = {},
          onConvert = {},
          isDropDownOpen = false,
          onUnitDropDownClick = {},
          onDropDownDismiss = {},
          onUnitSelected = {}
      )
    }
  }
  
}