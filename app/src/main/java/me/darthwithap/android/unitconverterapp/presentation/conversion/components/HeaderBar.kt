package me.darthwithap.android.unitconverterapp.presentation.conversion.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import me.darthwithap.android.unitconverterapp.R
import me.darthwithap.android.unitconverterapp.domain.models.Collection

@Composable
fun HeaderBar(
    modifier: Modifier = Modifier,
    currentCollection: UiCollection,
    collections: List<UiCollection> = emptyList(),
    isCollectionDropDownOpen: Boolean = false,
    onCollectionClick: () -> Unit,
    onDropDownDismiss: () -> Unit,
    onCollectionSelected: (Collection) -> Unit
) {
  Row(
      modifier = modifier
          .fillMaxWidth()
          .padding(4.dp),
      verticalAlignment = Alignment.CenterVertically
  ) {
    CollectionDropDown(
        modifier = Modifier.fillMaxWidth(0.5f),
        collection = currentCollection,
        collections = collections,
        isOpen = isCollectionDropDownOpen,
        onClick = onCollectionClick,
        onDismiss = onDropDownDismiss,
        onCollectionSelected = onCollectionSelected
    )
    Spacer(modifier = Modifier.weight(1f))
    Row(horizontalArrangement = Arrangement.SpaceBetween) {
      IconButton(onClick = {}) {
        Icon(
            modifier = Modifier
                .size(48.dp)
                .padding(horizontal = 10.dp),
            painter = painterResource(id = R.drawable.ic_history),
            contentDescription = stringResource(id = R.string.history)
        )
      }
      IconButton(onClick = {}) {
        Icon(
            modifier = Modifier
                .clickable { }
                .size(48.dp)
                .padding(horizontal = 8.dp),
            painter = painterResource(id = R.drawable.ic_batch_conversion),
            contentDescription = stringResource(id = R.string.history))
      }
      IconButton(onClick = {}) {
        Icon(
            modifier = Modifier
                .clickable { }
                .size(48.dp)
                .padding(horizontal = 8.dp),
            imageVector = Icons.Default.Favorite,
            contentDescription = stringResource(id = R.string.favourite))
      }
      IconButton(onClick = {}) {
        Icon(
            modifier = Modifier
                .clickable { }
                .size(48.dp)
                .padding(start = 8.dp, end = 12.dp),
            imageVector = Icons.Default.MoreVert,
            contentDescription = stringResource(id = R.string.options_menu))
      }
    }
  }
}