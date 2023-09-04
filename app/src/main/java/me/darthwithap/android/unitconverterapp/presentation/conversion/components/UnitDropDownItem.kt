package me.darthwithap.android.unitconverterapp.presentation.conversion.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.capitalize
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.intl.Locale
import androidx.compose.ui.unit.dp
import me.darthwithap.android.unitconverterapp.domain.models.SingleUnit

@Composable
fun UnitDropDownItem(
    modifier: Modifier = Modifier,
    unit: SingleUnit,
    symbolColor: Color,
    onClick: () -> Unit
) {
  DropdownMenuItem(text = {
    Row(
        modifier = Modifier.fillMaxWidth()
    ) {
      RoundedUnitSymbol(
          symbolColor = symbolColor,
          symbol = unit.symbol,
          horizontalPadding = 8.dp
      )
      Text(modifier = Modifier.padding(horizontal = 12.dp),
          text = unit.name.capitalize(Locale.current),
          color = MaterialTheme.colorScheme.onSurface,
          style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Medium)
      )
    }
  }, onClick = onClick, modifier = modifier)
}