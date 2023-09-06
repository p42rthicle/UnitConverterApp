package me.darthwithap.android.unitconverterapp.domain.usecases

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import me.darthwithap.android.unitconverterapp.util.ConversionError
import me.darthwithap.android.unitconverterapp.util.ConversionException
import me.darthwithap.android.unitconverterapp.util.ConversionResult

class ValidateInputUseCase {
  
  private val negativeAllowed = listOf("Temperature")
  
  suspend operator fun invoke(
      inputValue: String,
      collection: String
  ): Flow<ConversionResult<Boolean>> {
    // Rule: No characters other than digits and a decimal point.
    return flow {
      if (!inputValue.matches(Regex("^-?\\d*\\.?\\d*$"))) {
        emit(ConversionResult.Error(ConversionException(ConversionError.INVALID_CHARACTERS_FOUND)))
        emit(ConversionResult.Success(false))
        return@flow
      }
      
      // Decimal point can be at the start or middle but not at the end.
      if (inputValue.endsWith(".")) {
        emit(ConversionResult.Error(ConversionException(ConversionError.INVALID_DECIMAL_POSITION)))
        emit(ConversionResult.Success(false))
        return@flow
      }
      
      // Just a decimal point isn't valid.
      if (inputValue == ".") {
        emit(ConversionResult.Error(ConversionException(ConversionError.ONLY_DECIMAL_INVALID)))
        emit(ConversionResult.Success(false))
        return@flow
      }
      
      // Cant have negatives where it isn't allowed.
      if (inputValue.startsWith("-") && !negativeAllowed.contains(collection)) {
        emit(ConversionResult.Error(ConversionException(ConversionError.NEGATIVE_VALUES_NOT_ALLOWED)))
        emit(ConversionResult.Success(false))
        return@flow
      }
      
      emit(ConversionResult.Success(true))
    }.flowOn(Dispatchers.IO)
  }
}

