package me.darthwithap.android.unitconverterapp.presentation.conversion.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun RoundedUnitSymbol(
    modifier: Modifier = Modifier,
    symbolColor: Color,
    symbol: String,
    horizontalPadding: Dp = 12.dp,
    verticalPadding: Dp = 4.dp
) {
  Box(
      modifier
          .clip(RoundedCornerShape(100))
          .background(symbolColor)
          .padding(horizontal = horizontalPadding, vertical = verticalPadding),
      contentAlignment = Alignment.Center
  ) {
    Text(
        text = symbol,
        color = MaterialTheme.colorScheme.onSurface,
        style = MaterialTheme.typography.bodyLarge.copy(
            fontWeight = FontWeight.Medium), textAlign = TextAlign.Center
    )
  }
}