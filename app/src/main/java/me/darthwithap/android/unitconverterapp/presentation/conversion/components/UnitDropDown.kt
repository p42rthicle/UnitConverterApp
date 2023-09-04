package me.darthwithap.android.unitconverterapp.presentation.conversion.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.capitalize
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.intl.Locale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import me.darthwithap.android.unitconverterapp.R
import me.darthwithap.android.unitconverterapp.domain.models.SingleUnit

@Composable
fun UnitDropDown(
    modifier: Modifier = Modifier,
    units: List<SingleUnit> = emptyList(),
    singleUnit: SingleUnit,
    isOpen: Boolean = false,
    tintColor: Color,
    onClick: () -> Unit,
    onDismiss: () -> Unit,
    onUnitSelected: (SingleUnit) -> Unit
) {
  Box(modifier = modifier.padding(horizontal = 8.dp)) {
    DropdownMenu(expanded = isOpen, onDismissRequest = onDismiss) {
      units.forEach { unit ->
        UnitDropDownItem(
            unit = unit,
            symbolColor = tintColor
        ) { onUnitSelected(unit) }
      }
    }
    
    Row(
        modifier = Modifier.clickable { onClick() },
        verticalAlignment = Alignment.CenterVertically
    ) {
      Text(
          text = singleUnit.name.capitalize(Locale.current),
          style = TextStyle(fontSize = 18.sp, fontWeight = FontWeight.SemiBold))
      Spacer(modifier = Modifier.width(4.dp))
      Icon(
          modifier = Modifier.size(20.dp),
          imageVector = if (isOpen) Icons.Default.KeyboardArrowUp else Icons.Default.KeyboardArrowDown,
          contentDescription = if (isOpen) stringResource(id = R.string.close) else stringResource(id = R.string.open)
      )
    }
  }
}