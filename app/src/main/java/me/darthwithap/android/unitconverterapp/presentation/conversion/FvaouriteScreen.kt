package me.darthwithap.android.unitconverterapp.presentation.conversion

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import me.darthwithap.android.unitconverterapp.R
import me.darthwithap.android.unitconverterapp.domain.models.Conversion
import me.darthwithap.android.unitconverterapp.domain.models.ConversionUnits
import me.darthwithap.android.unitconverterapp.presentation.conversion.components.ConversionItem
import me.darthwithap.android.unitconverterapp.presentation.conversion.components.ConversionUnitsItem


@Composable
fun FavouriteScreen(
    modifier: Modifier = Modifier,
    state: ConversionState,
    onConversionClick: (Conversion) -> Unit,
    onConversionFavouriteClick: (Conversion) -> Unit,
    onConversionUnitsClick: (ConversionUnits) -> Unit,
    onConversionUnitsFavouriteClick: (ConversionUnits) -> Unit,
) {
  Box(
      modifier = modifier
          .fillMaxSize()
          .padding(16.dp)
          .background(MaterialTheme.colorScheme.surface)
  ) {
    if (state.favouriteConversions.isNotEmpty() || state.favouriteConversionUnits.isNotEmpty()) {
      LazyColumn {
        if (state.favouriteConversions.isNotEmpty()) {
          item {
            Text(
                modifier = Modifier.padding(vertical = 8.dp),
                text = stringResource(id = R.string.favourite_conversions),
                style = MaterialTheme.typography.headlineSmall.copy(
                    fontWeight = FontWeight.SemiBold,
                    color = MaterialTheme.colorScheme.onSurface
                )
            )
          }
        }
        items(state.favouriteConversions) { conversion ->
          ConversionItem(
              conversion = conversion,
              onFavouriteClick = onConversionFavouriteClick,
              onClick = onConversionClick
          )
        }
        if (state.favouriteConversionUnits.isNotEmpty()) {
          item {
            Spacer(modifier = Modifier.height(12.dp))
            Text(
                modifier = Modifier.padding(vertical = 8.dp),
                text = stringResource(id = R.string.favourite_conversion_units),
                style = MaterialTheme.typography.headlineSmall.copy(
                    fontWeight = FontWeight.SemiBold,
                    color = MaterialTheme.colorScheme.onSurface
                )
            )
          }
        }
        items(state.favouriteConversionUnits) { units ->
          ConversionUnitsItem(
              units = units,
              onFavouriteClick = onConversionUnitsFavouriteClick,
              onClick = onConversionUnitsClick,
          )
        }
      }
    } else {
      Column(
          modifier = Modifier.align(Alignment.Center),
          horizontalAlignment = Alignment.CenterHorizontally,
          verticalArrangement = Arrangement.Center) {
        Image(
            modifier = Modifier.size(64.dp),
            painter = painterResource(id = R.drawable.ic_sad),
            contentDescription = null
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            modifier = Modifier.padding(vertical = 8.dp),
            text = stringResource(id = R.string.no_favourite_conversions),
            style = MaterialTheme.typography.headlineSmall.copy(
                fontWeight = FontWeight.SemiBold,
                color = MaterialTheme.colorScheme.onSurface
            ),
            textAlign = TextAlign.Center
        )
        Text(
            modifier = Modifier.padding(vertical = 8.dp),
            text = stringResource(id = R.string.no_favourite_conversions_desc),
            style = MaterialTheme.typography.bodyLarge.copy(
                fontWeight = FontWeight.Medium,
                color = MaterialTheme.colorScheme.onSurface
            ),
            textAlign = TextAlign.Center
        )
      }
    }
  }
}