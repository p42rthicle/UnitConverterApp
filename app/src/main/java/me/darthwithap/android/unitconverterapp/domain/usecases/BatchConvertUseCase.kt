package me.darthwithap.android.unitconverterapp.domain.usecases

import androidx.core.text.isDigitsOnly
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import me.darthwithap.android.unitconverterapp.domain.models.SingleUnit
import me.darthwithap.android.unitconverterapp.util.ConversionError
import me.darthwithap.android.unitconverterapp.util.ConversionException
import me.darthwithap.android.unitconverterapp.util.ConversionResult
import kotlin.math.round

class BatchConvertUseCase {
  suspend operator fun invoke(
      input: String,
      fromUnit: SingleUnit,
      toUnits: List<SingleUnit>?
  ): Flow<ConversionResult<Map<SingleUnit, String>>> {
    return flow {
      val inputValue = input.toDoubleOrNull()
      
      if (inputValue == null) {
        emit(ConversionResult.Error(
            ConversionException(ConversionError.INVALID_INPUT_VALUE)
        ))
        return@flow
      }
      
      if (toUnits == null) {
        emit(ConversionResult.Error(
            ConversionException(ConversionError.GENERAL_ERROR)
        ))
        return@flow
      }
      
      val conversionOutputs = mutableMapOf<SingleUnit, String>()
      
      for (toUnit in toUnits) {
        if (fromUnit.collectionName != toUnit.collectionName) {
          emit(ConversionResult.Error(
              ConversionException(ConversionError.DIFFERENT_COLLECTIONS)
          ))
          return@flow
        }
        
        val outputValue = if (fromUnit.name != toUnit.name) {
          performConversion(inputValue, fromUnit, toUnit)
        } else customRound(inputValue)
        conversionOutputs[toUnit] = outputValue
      }
      
      emit(ConversionResult.Success(conversionOutputs.toMap()))
    }.flowOn(Dispatchers.IO)
  }
  
  private fun performConversion(
      inputValue: Double,
      fromUnit: SingleUnit,
      toUnit: SingleUnit
  ): String {
    val standardValue = (inputValue - fromUnit.offset) / fromUnit.multiplier
    val outputValue = (standardValue * toUnit.multiplier) + toUnit.offset
    return customRound(outputValue)
  }
  
  private fun customRound(value: Double): String {
    val rounded = round(value * 100000000000000) / 100000000000000.0
    return if (rounded == rounded.toInt().toDouble()) {
      rounded.toInt().toString() // returns "10" instead of "10.0"
    } else { // Trim trailing 0s in decimals
      String.format("%.14f", rounded).trimEnd('0').trimEnd('.')
    }
  }
}

