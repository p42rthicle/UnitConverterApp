package me.darthwithap.android.unitconverterapp.domain.usecases

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import me.darthwithap.android.unitconverterapp.domain.models.Conversion
import me.darthwithap.android.unitconverterapp.domain.repository.ConverterRepository
import me.darthwithap.android.unitconverterapp.util.ConversionException
import me.darthwithap.android.unitconverterapp.util.ConversionResult

class FavouriteConversionsUseCase(
    private val repository: ConverterRepository
) {
  suspend operator fun invoke(): Flow<ConversionResult<List<Conversion>>> {
    return flow {
      
      try {
        repository.getFavouriteConversions().collect {
          emit(ConversionResult.Success(it))
        }
      } catch (e: ConversionException) {
        emit(ConversionResult.Error(e))
      }
    }
  }
}