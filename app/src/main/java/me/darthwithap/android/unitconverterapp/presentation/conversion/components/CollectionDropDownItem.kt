package me.darthwithap.android.unitconverterapp.presentation.conversion.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp

@Composable
fun CollectionDropDownItem(
    modifier: Modifier = Modifier,
    collection: UiCollection,
    onClick: () -> Unit
) {
  DropdownMenuItem(text = {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
      Icon(painter = painterResource(id = collection.drawableRes), contentDescription = collection.collection.name, tint = collection.tintColor)
      Spacer(modifier = Modifier.width(4.dp))
      Text(text = collection.collection.name, style = MaterialTheme.typography.bodyLarge)
    }
  }, onClick = onClick, modifier = modifier)
}