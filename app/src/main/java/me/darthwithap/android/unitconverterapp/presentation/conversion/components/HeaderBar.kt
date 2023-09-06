package me.darthwithap.android.unitconverterapp.presentation.conversion.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
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
    isBatchConversion: Boolean = false,
    showMenu: Boolean = false,
    onBatchIconClick: () -> Unit,
    onHistoryIconClick: () -> Unit,
    onFavouriteIconClick: () -> Unit,
    onCollectionClick: () -> Unit,
    onDropDownDismiss: () -> Unit,
    onHideOptionsMenu: () -> Unit,
    onShowOptionsMenu: () -> Unit,
    onAboutUsClick: () -> Unit,
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
      IconButton(onClick = onHistoryIconClick) {
        Icon(
            modifier = Modifier
                .size(48.dp)
                .padding(horizontal = 10.dp),
            painter = painterResource(id = R.drawable.ic_history),
            contentDescription = stringResource(id = R.string.history)
        )
      }
      IconButton(onClick = onBatchIconClick) {
        Icon(
            modifier = Modifier
                .size(48.dp)
                .padding(horizontal = 8.dp),
            painter = painterResource(
                id = if (isBatchConversion)
                  R.drawable.ic_simple_conversion
                else R.drawable.ic_batch_conversion
            ),
            contentDescription = stringResource(id = R.string.history))
      }
      IconButton(onClick = onFavouriteIconClick) {
        Icon(
            modifier = Modifier
                .size(48.dp)
                .padding(horizontal = 8.dp),
            imageVector = Icons.Default.Favorite,
            contentDescription = stringResource(id = R.string.favourite))
      }
      Box {
        IconButton(onClick = onShowOptionsMenu) {
          Icon(
              modifier = Modifier
                  .size(48.dp)
                  .padding(start = 8.dp, end = 12.dp),
              imageVector = Icons.Default.MoreVert,
              contentDescription = stringResource(id = R.string.options_menu)
          )
        }
        DropdownMenu(
            expanded = showMenu,
            onDismissRequest = onHideOptionsMenu,
            modifier = Modifier.align(Alignment.TopEnd)
        ) {
          DropdownMenuItem(text = {
            Text(text = stringResource(id = R.string.about_us), style = MaterialTheme.typography.bodyLarge)
          }, onClick = onAboutUsClick)
        }
      }
      
    }
  }
}