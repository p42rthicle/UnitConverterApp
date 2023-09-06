package me.darthwithap.android.unitconverterapp.presentation.conversion.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import me.darthwithap.android.unitconverterapp.R
import me.darthwithap.android.unitconverterapp.domain.models.SingleUnit

@Composable
fun ConversionBottomSheet(
    modifier: Modifier = Modifier,
    fromUnit: SingleUnit,
    toUnit: SingleUnit,
    inputValue: String,
    outputValue: String,
    onFavouriteClick: () -> Unit,
    onCopyClick: () -> Unit
) {
  Column {
    Row(
        modifier = Modifier
            .clip(RoundedCornerShape(8.dp))
            .background(Color.Transparent)
            .border(1.dp, MaterialTheme.colorScheme.onSurface, RoundedCornerShape(8.dp))
            .clickable { onFavouriteClick() }
            .padding(4.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
      Icon(
          modifier = Modifier
              .size(24.dp)
              .padding(4.dp),
          painter = painterResource(id = R.drawable.ic_sad),
          contentDescription = null
      )
      Spacer(modifier = Modifier.width(4.dp))
      Text(
          modifier = Modifier.padding(4.dp),
          text = stringResource(id = R.string.swap),
          style = MaterialTheme.typography.bodyMedium.copy(
              fontWeight = FontWeight.Medium,
              color = MaterialTheme.colorScheme.onSurface)
      )
    }
  }
}