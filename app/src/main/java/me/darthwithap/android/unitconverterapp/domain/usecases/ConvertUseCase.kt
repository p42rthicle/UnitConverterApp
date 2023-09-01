package me.darthwithap.android.unitconverterapp.domain.usecases

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext
import me.darthwithap.android.unitconverterapp.domain.models.Conversion
import me.darthwithap.android.unitconverterapp.domain.models.ConversionOutput
import me.darthwithap.android.unitconverterapp.domain.models.SingleUnit
import me.darthwithap.android.unitconverterapp.domain.repository.ConverterRepository
import me.darthwithap.android.unitconverterapp.util.ConversionError
import me.darthwithap.android.unitconverterapp.util.ConversionException
import me.darthwithap.android.unitconverterapp.util.ConversionResult

class ConvertUseCase(
  private val repository: ConverterRepository
) {
  suspend operator fun invoke(
    inputValue: Double,
    fromUnit: SingleUnit,
    toUnit: SingleUnit
  ): Flow<ConversionResult<ConversionOutput>> {
    return flow {
      emit(ConversionResult.Loading())
      // Early failure checks
      if (inputValue <= 0)
        throw ConversionException(ConversionError.INVALID_INPUT_VALUE)
      if (fromUnit.collection != toUnit.collection)
        throw ConversionException(ConversionError.DIFFERENT_COLLECTIONS)

      val outputValue = performConversion(inputValue, fromUnit, toUnit)
      emit(ConversionResult.Success(ConversionOutput(outputValue)))
      try {
        withContext(Dispatchers.IO) {
          val generatedId = repository.updateConversion(
            // id field set to 0L to let Room auto-generate it
            Conversion(
              0L, fromUnit.name, toUnit.name, inputValue, outputValue, fromUnit.collection
            )
          )
          emit(ConversionResult.Success(ConversionOutput(outputValue, generatedId)))
        }
      } catch (e: ConversionException) {
        emit(ConversionResult.Error(e))
      }
    }
  }

  private fun performConversion(
    inputValue: Double,
    fromUnit: SingleUnit,
    toUnit: SingleUnit
  ): Double {
    val standardValue = (inputValue - fromUnit.offset) / fromUnit.multiplier
    return standardValue * toUnit.multiplier + toUnit.offset
  }
}