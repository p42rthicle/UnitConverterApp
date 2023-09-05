package me.darthwithap.android.unitconverterapp.domain.usecases

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.withContext
import me.darthwithap.android.unitconverterapp.domain.models.ConversionUnits
import me.darthwithap.android.unitconverterapp.domain.models.SingleUnit
import me.darthwithap.android.unitconverterapp.domain.repository.ConverterRepository
import me.darthwithap.android.unitconverterapp.util.ConversionError
import me.darthwithap.android.unitconverterapp.util.ConversionException
import me.darthwithap.android.unitconverterapp.util.ConversionResult

class AddConversionUnitsUseCase(
    private val repository: ConverterRepository
) {
  suspend operator fun invoke(
      fromUnit: SingleUnit,
      toUnit: SingleUnit
  ): ConversionResult<Long> {
    return try {
      withContext(Dispatchers.IO) {
        if (fromUnit.collectionName != toUnit.collectionName)
          return@withContext ConversionResult.Error(ConversionException(ConversionError.DIFFERENT_COLLECTIONS))
        
        val unitsToUpdate = repository.getRecentConversionUnitsByUnits(fromUnit.name, toUnit.name)
            .firstOrNull()
            ?.takeIf {
              it.fromUnit.name == fromUnit.name && it.toUnit.name == toUnit.name && it.collection == fromUnit.collectionName
            }
            ?.copy(timestamp = System.currentTimeMillis())
            ?: ConversionUnits(fromUnit = fromUnit, toUnit = toUnit, collection = fromUnit.collectionName)
        
        val rowsUpdated = repository.updateConversionUnits(unitsToUpdate)
        ConversionResult.Success(rowsUpdated)
      }
    } catch (e: ConversionException) {
      e.printStackTrace()
      ConversionResult.Error(e)
    }
  }
}