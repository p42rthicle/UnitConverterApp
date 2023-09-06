package me.darthwithap.android.unitconverterapp.domain.usecases

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import me.darthwithap.android.unitconverterapp.domain.models.Conversion
import me.darthwithap.android.unitconverterapp.domain.models.ConversionOutput
import me.darthwithap.android.unitconverterapp.domain.models.SingleUnit
import me.darthwithap.android.unitconverterapp.domain.repository.ConverterRepository
import me.darthwithap.android.unitconverterapp.util.ConversionError
import me.darthwithap.android.unitconverterapp.util.ConversionException
import me.darthwithap.android.unitconverterapp.util.ConversionResult
import kotlin.math.round

class ConvertUseCase(
    private val repository: ConverterRepository
) {
  
  suspend operator fun invoke(
      input: String,
      fromUnit: SingleUnit,
      toUnit: SingleUnit,
      saveToHistory: Boolean = false
  ): Flow<ConversionResult<ConversionOutput>> {
    
    return flow {
      if (fromUnit.collectionName != toUnit.collectionName) {
        emit(ConversionResult.Error(
            ConversionException(ConversionError.DIFFERENT_COLLECTIONS)
        ))
        return@flow
      }
      val inputValue = input.toDoubleOrNull()
      
      if (inputValue == null) {
        emit(ConversionResult.Error(
            ConversionException(ConversionError.INVALID_INPUT_VALUE)
        ))
        return@flow
      }
      
      val outputValue = performConversion(inputValue, fromUnit, toUnit)
      emit(ConversionResult.Success(ConversionOutput(outputValue)))
      
      // Save to db when onDone action from keyboard
      if (saveToHistory) {
        try {
          repository.updateConversion(
              //TODO: Don't have to toDouble() here instead make Conversion have Strings instead
              Conversion(0L, fromUnit, toUnit, inputValue, outputValue.toDouble(), fromUnit.collectionName)
          )
        } catch (e: ConversionException) {
          emit(ConversionResult.Error(e))
        }
      }
    }.flowOn(Dispatchers.IO)
  }
  
  private fun performConversion(
      inputValue: Double,
      fromUnit: SingleUnit,
      toUnit: SingleUnit
  ): String {
    val standardValue = inputValue / fromUnit.multiplier + fromUnit.offset
    val outputValue = (standardValue - toUnit.offset) * toUnit.multiplier
    return customRound(outputValue)
  }
}

private fun customRound(value: Double): String {
  val rounded = round(value * 100000000000000) / 100000000000000.0
  //if (rounded.toInt() == 0) return ""
  return if (rounded == rounded.toInt().toDouble()) {
    rounded.toInt().toString() // returns "10" instead of "10.0"
  } else {
    String.format("%.14f", rounded).trimEnd('0').trimEnd('.') // trims any trailing zeros and any trailing decimal points
  }
}