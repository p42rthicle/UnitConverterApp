package me.darthwithap.android.unitconverterapp.domain.usecases

import me.darthwithap.android.unitconverterapp.domain.models.ConversionUnits
import me.darthwithap.android.unitconverterapp.domain.repository.ConverterRepository
import me.darthwithap.android.unitconverterapp.util.ConversionException
import me.darthwithap.android.unitconverterapp.util.ConversionResult

class DeleteConversionUnitsUseCase(
    private val repository: ConverterRepository
) {
  suspend operator fun invoke(units: ConversionUnits): ConversionResult<String> {
    return try {
      repository.deleteConversionUnits(units)
      ConversionResult.Success("Deleted successfully")
    } catch (e: ConversionException) {
      e.printStackTrace()
      ConversionResult.Error(e)
    }
  }
}