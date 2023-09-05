package me.darthwithap.android.unitconverterapp.presentation.conversion.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.capitalize
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.intl.Locale
import androidx.compose.ui.unit.dp
import me.darthwithap.android.unitconverterapp.R
import me.darthwithap.android.unitconverterapp.domain.models.Conversion

@Composable
fun ConversionItem(
    modifier: Modifier = Modifier,
    conversion: Conversion,
    onFavouriteClick: (Conversion) -> Unit,
    onClick: (Conversion) -> Unit
) {
  Box(modifier = modifier
      .fillMaxWidth()
      .padding(4.dp)
      .clip(RoundedCornerShape(12.dp))
      .background(MaterialTheme.colorScheme.background)
      .clickable { onClick(conversion) }
      .padding(8.dp)
  ) {
    val fromText = "${conversion.inputValue} ${conversion.fromUnit.name.capitalize(Locale.current)} ="
    val toText = "${conversion.outputValue} ${conversion.toUnit.name.capitalize(Locale.current)}"
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically) {
      Column(horizontalAlignment = Alignment.Start) {
        Text(
            text = fromText,
            style = MaterialTheme.typography.bodyMedium.copy(
                fontWeight = FontWeight.Normal,
                color = MaterialTheme.colorScheme.onSecondary)
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = toText,
            style = MaterialTheme.typography.bodyLarge.copy(
                fontWeight = FontWeight.Medium,
                color = MaterialTheme.colorScheme.onBackground)
        )
      }
      Icon(modifier = Modifier
          .padding(horizontal = 16.dp, vertical = 4.dp)
          .clickable(
              interactionSource = MutableInteractionSource(),
              indication = null,
              onClick = { onFavouriteClick(conversion) }
          ),
          imageVector = if (conversion.isFavourite) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
          contentDescription = stringResource(id = R.string.toggle_favourite)
      )
    }
  }
}