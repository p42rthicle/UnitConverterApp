package me.darthwithap.android.unitconverterapp.domain.usecases

import me.darthwithap.android.unitconverterapp.domain.models.Conversion
import me.darthwithap.android.unitconverterapp.domain.repository.ConverterRepository
import me.darthwithap.android.unitconverterapp.util.ConversionException
import me.darthwithap.android.unitconverterapp.util.ConversionResult

class DeleteConversionUseCase(
  private val repository: ConverterRepository
) {
  suspend operator fun invoke(conversion: Conversion): ConversionResult<String> {
    return try {
      repository.deleteConversion(conversion)
      ConversionResult.Success("Deleted successfully")
    } catch (e: ConversionException) {
      e.printStackTrace()
      ConversionResult.Error(e)
    }
  }
}