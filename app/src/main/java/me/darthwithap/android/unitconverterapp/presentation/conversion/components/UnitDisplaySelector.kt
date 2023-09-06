package me.darthwithap.android.unitconverterapp.presentation.conversion.components

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.capitalize
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.intl.Locale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Popup
import me.darthwithap.android.unitconverterapp.R
import me.darthwithap.android.unitconverterapp.domain.models.SingleUnit

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun UnitDisplaySelector(
    modifier: Modifier = Modifier,
    uiCollection: UiCollection?,
    singleUnit: SingleUnit,
    value: String,
    isEditable: Boolean = false,
    isDropDown: Boolean = true,
    hasMoreMenu: Boolean = false,
    onMoreMenuClick: () -> Unit = {},
    onInputValueChanged: (String) -> Unit = { },
    onConvert: () -> Unit,
    isDropDownOpen: Boolean = false,
    onUnitDropDownClick: () -> Unit,
    onDropDownDismiss: () -> Unit,
    onUnitSelected: (SingleUnit) -> Unit
) {
  val focusManager = LocalFocusManager.current
  val keyboardController = LocalSoftwareKeyboardController.current
  val boxHorizontalPadding = if (hasMoreMenu) 8.dp else 16.dp
  val boxVerticalPadding = if (hasMoreMenu) 12.dp else 32.dp
  var showContextMenu by remember { mutableStateOf(false) }
  val clipboardManager = LocalClipboardManager.current
  
  Box(
      modifier = modifier
          .clip(RoundedCornerShape(16.dp))
          .background(
              if (isEditable) MaterialTheme.colorScheme.surfaceVariant
              else MaterialTheme.colorScheme.background
          )
          .padding(
              horizontal = boxHorizontalPadding,
              vertical = boxVerticalPadding
          )
  
  ) {
    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
      Column(
          modifier = Modifier.fillMaxWidth(if (hasMoreMenu) 0.85f else 1f),
          horizontalAlignment = Alignment.Start,
          verticalArrangement = Arrangement.SpaceEvenly) {
        if (isDropDown) {
          UnitDropDown(
              modifier = Modifier.padding(horizontal = 8.dp),
              units = uiCollection?.collection?.units ?: emptyList(),
              singleUnit = singleUnit,
              tintColor = uiCollection?.tintColor ?: MaterialTheme.colorScheme.primary,
              isOpen = isDropDownOpen,
              onClick = onUnitDropDownClick,
              onDismiss = onDropDownDismiss,
              onUnitSelected = onUnitSelected
          )
        } else {
          Text(
              modifier = Modifier.padding(horizontal = 8.dp),
              text = singleUnit.name.capitalize(Locale.current),
              style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.SemiBold)
          )
        }
        if (!hasMoreMenu) {
          Spacer(modifier = Modifier.height(10.dp))
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp),
            horizontalArrangement = Arrangement.End,
            verticalAlignment = Alignment.CenterVertically
        ) {
          if (!isEditable) {
            Text(
                text = value,
                style = if (!hasMoreMenu)
                  MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.SemiBold)
                else MaterialTheme.typography.headlineSmall.copy(
                    fontWeight = FontWeight.SemiBold,
                    color = MaterialTheme.colorScheme.onBackground),
                color = MaterialTheme.colorScheme.onSurface
            )
          } else {
            BasicTextField(
                modifier = Modifier
                    .pointerInput(Unit) {
                      detectTapGestures {
                        if (clipboardManager.hasText()) {
                          showContextMenu = true
                        }
                      }
                    },
                value = value,
                onValueChange = onInputValueChanged,
                maxLines = 1,
                textStyle = MaterialTheme.typography.headlineMedium.copy(
                    fontWeight = FontWeight.SemiBold,
                    color = MaterialTheme.colorScheme.onSurface,
                    textAlign = TextAlign.End
                ),
                keyboardActions = KeyboardActions(onDone = {
                  onConvert()
                  focusManager.clearFocus()
                  keyboardController?.hide()
                }),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number,
                    imeAction = ImeAction.Done
                ),
                singleLine = true,
            )
            
            if (showContextMenu) {
              Popup(
                  onDismissRequest = { showContextMenu = false }
              ) {
                Box(modifier = Modifier
                    .background(Color.White)
                    .padding(16.dp)
                    .pointerInput(Unit) {
                      detectTapGestures {
                        val clip = clipboardManager.getText()
                        if (!clip.isNullOrEmpty()) {
                          onInputValueChanged(clip.text)
                        }
                        showContextMenu = false
                      }
                    }) {
                  Text("Paste")
                }
              }
            }
          }
          
          Spacer(modifier = Modifier.width(12.dp))
          RoundedUnitSymbol(symbolColor = uiCollection?.tintColor
              ?: MaterialTheme.colorScheme.primary, symbol = singleUnit.symbol)
        }
      }
      if (hasMoreMenu) {
        IconButton(onClick = onMoreMenuClick) {
          Icon(
              painter = painterResource(id = R.drawable.ic_dots_horizontal),
              contentDescription = stringResource(id = R.string.more_menu),
              tint = MaterialTheme.colorScheme.onSurface
          )
        }
      }
    }
  }
}
