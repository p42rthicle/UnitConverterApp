package me.darthwithap.android.unitconverterapp.presentation.conversion

import me.darthwithap.android.unitconverterapp.domain.models.Collection
import me.darthwithap.android.unitconverterapp.domain.models.Conversion
import me.darthwithap.android.unitconverterapp.domain.models.ConversionUnits
import me.darthwithap.android.unitconverterapp.domain.models.SingleUnit

sealed class ConversionEvent {
  data class Convert(val saveToHistory: Boolean = false) : ConversionEvent()
  data class ToggleFavouriteConversion(val conversion: Conversion) : ConversionEvent()
  data class ToggleFavouriteConversionUnits(val units: ConversionUnits) : ConversionEvent()
  object ChoosingFromUnit : ConversionEvent()
  object ChoosingToUnit : ConversionEvent()
  object ChoosingCollection : ConversionEvent()
  object StoppedChoosingFromUnit : ConversionEvent()
  object StoppedChoosingToUnit : ConversionEvent()
  object ResetConversion : ConversionEvent()
  object ToggleBatchConversion : ConversionEvent()
  object StoppedChoosingCollection : ConversionEvent()
  data class DeleteConversion(val conversion: Conversion) : ConversionEvent()
  data class DeleteConversionUnits(val units: ConversionUnits) : ConversionEvent()
  data class ChosenFromUnit(
      val unit: SingleUnit,
      val isBatchConversion: Boolean = false
  ) : ConversionEvent()
  
  data class ChosenBatchConversionFromUnit(val unit: SingleUnit) : ConversionEvent()
  data class ChosenToUnit(val unit: SingleUnit) : ConversionEvent()
  data class ChosenCollection(val collection: Collection) : ConversionEvent()
  data class ChosenConversionUnits(val units: ConversionUnits) : ConversionEvent()
  data class ChosenConversion(val conversion: Conversion) : ConversionEvent()
  data class InputValueChanged(
      val value: String,
      val isBatchConversion: Boolean = false
  ) : ConversionEvent()
  
  data class PerformBatchConversion(val value: String) : ConversionEvent()
  object OnErrorSeen : ConversionEvent()
  
}