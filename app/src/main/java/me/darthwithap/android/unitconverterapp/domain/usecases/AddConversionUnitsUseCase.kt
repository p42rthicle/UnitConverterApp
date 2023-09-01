package me.darthwithap.android.unitconverterapp.domain.usecases

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import me.darthwithap.android.unitconverterapp.domain.models.ConversionUnits
import me.darthwithap.android.unitconverterapp.domain.repository.ConverterRepository
import me.darthwithap.android.unitconverterapp.util.ConversionException
import me.darthwithap.android.unitconverterapp.util.ConversionResult

class AddConversionUnitsUseCase(
  private val repository: ConverterRepository
) {
  suspend operator fun invoke(
    units: ConversionUnits
  ): ConversionResult<Long> {
    return try {
      withContext(Dispatchers.IO) {
        val generatedId = repository.updateConversionUnits(units)
        ConversionResult.Success(generatedId)
      }
    } catch (e: ConversionException) {
      e.printStackTrace()
      ConversionResult.Error(e)
    }
  }
}