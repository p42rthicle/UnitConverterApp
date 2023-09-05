package me.darthwithap.android.unitconverterapp.presentation.conversion.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.capitalize
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.intl.Locale
import androidx.compose.ui.unit.dp
import me.darthwithap.android.unitconverterapp.R
import me.darthwithap.android.unitconverterapp.domain.models.ConversionUnits

@Composable
fun ConversionUnitsItem(
    modifier: Modifier = Modifier,
    units: ConversionUnits,
    onFavouriteClick: (ConversionUnits) -> Unit,
    onClick: (ConversionUnits) -> Unit
) {
  Box(
      modifier = modifier
          .fillMaxWidth()
          .padding(4.dp)
          .clip(RoundedCornerShape(12.dp))
          .background(MaterialTheme.colorScheme.background)
          .clickable { onClick(units) }
          .padding(8.dp)
  ) {
    Row(modifier = Modifier
        .fillMaxWidth()
        .padding(horizontal = 8.dp),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically) {
      Text(
          text = units.fromUnit.name.capitalize(Locale.current),
          style = MaterialTheme.typography.bodyMedium.copy(
              fontWeight = FontWeight.Normal,
              color = MaterialTheme.colorScheme.onSecondary)
      )
      Spacer(modifier = Modifier.weight(1f))
      Icon(
          painter = painterResource(id = R.drawable.ic_swap),
          contentDescription = stringResource(id = R.string.swap_units))
      Spacer(modifier = Modifier.weight(1f))
      Text(
          text = units.toUnit.name.capitalize(Locale.current),
          style = MaterialTheme.typography.bodyLarge.copy(
              fontWeight = FontWeight.Medium,
              color = MaterialTheme.colorScheme.onBackground)
      )
      Spacer(modifier = Modifier.weight(1f))
      Icon(modifier = Modifier
          .padding(4.dp)
          .clickable(
              interactionSource = MutableInteractionSource(),
              indication = null,
              onClick = { onFavouriteClick(units) }
          ),
          imageVector = if (units.isFavourite) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
          contentDescription = stringResource(id = R.string.toggle_favourite)
      )
    }
  }
}