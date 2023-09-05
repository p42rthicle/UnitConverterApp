package me.darthwithap.android.unitconverterapp.domain.usecases

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import me.darthwithap.android.unitconverterapp.domain.models.Collection
import me.darthwithap.android.unitconverterapp.domain.repository.ConverterRepository
import me.darthwithap.android.unitconverterapp.util.ConversionException
import me.darthwithap.android.unitconverterapp.util.ConversionResult

class CollectionsUseCase(
    private val repository: ConverterRepository
) {
  suspend operator fun invoke(): Flow<ConversionResult<List<Collection>>> {
    return flow {
      try {
        repository.getCollections().collect {
          emit(ConversionResult.Success(it))
        }
      } catch (e: ConversionException) {
        emit(ConversionResult.Error(e))
      }
    }
  }
}
