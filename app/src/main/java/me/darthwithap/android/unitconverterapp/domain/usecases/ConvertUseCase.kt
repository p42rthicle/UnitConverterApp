package me.darthwithap.android.unitconverterapp.domain.usecases

import androidx.core.text.isDigitsOnly
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
      toUnit: SingleUnit
  ): Flow<ConversionResult<ConversionOutput>> {
    
    return flow {
      // Early failure checks
      if (!input.isDigitsOnly()) emit(ConversionResult.Error(
          ConversionException(ConversionError.INVALID_INPUT_VALUE)
      ))
      val inputValue = input.toDouble()
      if (inputValue < 0) emit(ConversionResult.Error(
          ConversionException(ConversionError.INVALID_INPUT_VALUE)
      ))
      if (fromUnit.collectionName != toUnit.collectionName)
        emit(ConversionResult.Error(
            ConversionException(ConversionError.DIFFERENT_COLLECTIONS)
        ))
      
      val outputValue = performConversion(inputValue, fromUnit, toUnit)
      emit(ConversionResult.Success(ConversionOutput(outputValue)))
      
      try {
        val generatedId = repository.updateConversion(
            // id field set to 0L to let Room auto-generate it
            //TODO: Don't have to toDouble() here instead make Conversion have Strings instead
            Conversion(
                0L, fromUnit, toUnit, inputValue, outputValue.toDouble(), fromUnit.collectionName
            )
        )
        //TODO: when checking if saved then make ability to favourite available
        //emit(ConversionResult.Success(ConversionOutput(outputValue, generatedId)))
      } catch (e: ConversionException) {
        emit(ConversionResult.Error(e))
      }
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
}

private fun customRound(value: Double): String {
  val rounded = round(value * 1000) / 1000.0
  //if (rounded.toInt() == 0) return ""
  return if (rounded == rounded.toInt().toDouble()) {
    rounded.toInt().toString() // returns "10" instead of "10.0"
  } else {
    String.format("%.3f", rounded).trimEnd('0').trimEnd('.') // trims any trailing zeros and any trailing decimal points
  }
}