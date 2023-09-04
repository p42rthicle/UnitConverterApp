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
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import me.darthwithap.android.unitconverterapp.R
import me.darthwithap.android.unitconverterapp.domain.models.Collection

@Composable
fun CollectionDropDown(
    modifier: Modifier = Modifier,
    collections: List<UiCollection> = emptyList(),
    collection: UiCollection,
    isOpen: Boolean = false,
    onClick: () -> Unit,
    onDismiss: () -> Unit,
    onCollectionSelected: (Collection) -> Unit
) {
  Box(modifier = modifier.padding(16.dp)) {
    DropdownMenu(
        expanded = isOpen,
        onDismissRequest = onDismiss
    ) {
      collections.forEach { collectionItem ->
        CollectionDropDownItem(collection = collectionItem) {
          onCollectionSelected(collectionItem.collection)
        }
      }
    }
    // UI when the dropdown menu is collapsed / not expanded
    Row(
        modifier = Modifier
            .clickable { onClick() },
        verticalAlignment = Alignment.CenterVertically
    ) {
      Icon(
          modifier = Modifier.size(28.dp),
          painter = painterResource(id = collection.drawableRes),
          contentDescription = collection.collection.name,
          tint = collection.tintColor)
      Spacer(modifier = Modifier.width(8.dp))
      Text(
          text = collection.collection.name,
          style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.SemiBold))
      Spacer(modifier = Modifier.width(4.dp))
      Icon(
          imageVector = if (isOpen) Icons.Default.KeyboardArrowUp else Icons.Default.KeyboardArrowDown,
          contentDescription = if (isOpen) stringResource(R.string.close)
          else stringResource(R.string.open),
          modifier = Modifier.size(24.dp)
      )
    }
  }
}