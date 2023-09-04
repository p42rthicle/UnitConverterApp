package me.darthwithap.android.unitconverterapp.domain.usecases

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import me.darthwithap.android.unitconverterapp.domain.models.ConversionUnits
import me.darthwithap.android.unitconverterapp.domain.repository.ConverterRepository
import me.darthwithap.android.unitconverterapp.util.ConversionException
import me.darthwithap.android.unitconverterapp.util.ConversionResult

class RecentConversionUnitsUseCase(
  private val repository: ConverterRepository
) {
  suspend operator fun invoke(): Flow<ConversionResult<List<ConversionUnits>>> {
    return flow {

      try {
        repository.getFavouriteConversionUnits().collect {
          emit(ConversionResult.Success(it))
        }
      } catch (e: ConversionException) {
        emit(ConversionResult.Error(e))
      }
    }
  }
}