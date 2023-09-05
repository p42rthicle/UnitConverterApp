package me.darthwithap.android.unitconverterapp.domain.usecases

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import me.darthwithap.android.unitconverterapp.domain.models.Conversion
import me.darthwithap.android.unitconverterapp.domain.repository.ConverterRepository
import me.darthwithap.android.unitconverterapp.util.ConversionException
import me.darthwithap.android.unitconverterapp.util.ConversionResult

class ToggleFavouriteConversionUseCase(
    private val repository: ConverterRepository
) {
  suspend operator fun invoke(
      conversion: Conversion
  ): ConversionResult<Long> {
    return try {
      withContext(Dispatchers.IO) {
        val updatedConversion = conversion.copy(isFavourite = !conversion.isFavourite)
        val numOfRowsUpdated = repository.updateConversion(updatedConversion)
        ConversionResult.Success(numOfRowsUpdated)
      }
    } catch (e: ConversionException) {
      ConversionResult.Error(e)
    }
  }
}
