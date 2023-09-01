package me.darthwithap.android.unitconverterapp.data.repository

import android.database.SQLException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import me.darthwithap.android.unitconverterapp.data.db.DbFileReader
import me.darthwithap.android.unitconverterapp.data.local.ConversionDao
import me.darthwithap.android.unitconverterapp.domain.models.Collection
import me.darthwithap.android.unitconverterapp.domain.models.Conversion
import me.darthwithap.android.unitconverterapp.domain.models.ConversionUnits
import me.darthwithap.android.unitconverterapp.domain.repository.ConverterRepository
import me.darthwithap.android.unitconverterapp.util.ConversionError
import me.darthwithap.android.unitconverterapp.util.ConversionException

class ConverterRepositoryImpl constructor(
  private val dbFileReader: DbFileReader,
  private val dao: ConversionDao
) : ConverterRepository {
  override suspend fun updateConversion(conversion: Conversion): Long {
    try {
      return dao.updateConversion(conversion.toEntity())
    } catch (e: SQLException) {
      e.printStackTrace()
      throw ConversionException(ConversionError.DB_ERROR)
    } catch (e: Exception) {
      e.printStackTrace()
      throw ConversionException(ConversionError.GENERAL_ERROR)
    }
  }

  override fun getRecentConversions(): Flow<List<Conversion>> {
    return dao.getRecentConversions().mapEntitiesToDomain {
      it.toDomainModel()
    }.catch { e ->
      e.printStackTrace()
      throw ConversionException(ConversionError.DB_ERROR)
    }
  }

  override fun getFavouriteConversions(): Flow<List<Conversion>> {
    return dao.getFavouriteConversions().mapEntitiesToDomain {
      it.toDomainModel()
    }.catch { e ->
      e.printStackTrace()
      throw ConversionException(ConversionError.DB_ERROR)
    }
  }

  override suspend fun deleteConversion(conversion: Conversion) {
    try {
      dao.deleteConversion(conversion.toEntity())
    } catch (e: Exception) {
      e.printStackTrace()
      throw ConversionException(ConversionError.DB_ERROR)
    }
  }

  override suspend fun updateConversionUnits(units: ConversionUnits): Long {
    try {
      return dao.updateConversionUnits(units.toEntity())
    } catch (e: SQLException) {
      e.printStackTrace()
      throw ConversionException(ConversionError.DB_ERROR)
    } catch (e: Exception) {
      e.printStackTrace()
      throw ConversionException(ConversionError.GENERAL_ERROR)
    }
  }

  override fun getRecentConversionUnits(): Flow<List<ConversionUnits>> {
    return dao.getRecentConversionUnits().mapEntitiesToDomain {
      it.toDomainModel()
    }
      .catch { e ->
        e.printStackTrace()
        throw ConversionException(ConversionError.DB_ERROR)
      }
  }

  override fun getFavouriteConversionUnits(): Flow<List<ConversionUnits>> {
    return dao.getFavouriteConversionUnits().mapEntitiesToDomain {
      it.toDomainModel()
    }
      .catch { e ->
        e.printStackTrace()
        throw ConversionException(ConversionError.DB_ERROR)
      }
  }

  override suspend fun deleteConversionUnits(units: ConversionUnits) {
    try {
      dao.deleteConversionUnits(units.toEntity())
    } catch (e: Exception) {
      e.printStackTrace()
      throw ConversionException(ConversionError.DB_ERROR)
    }
  }

  override suspend fun getCollections(): Flow<List<Collection>> {
    return flow {
      val collections = dbFileReader.getAllCollections().map { it.toDomainModel() }
      emit(collections)
    }
      .flowOn(Dispatchers.IO)
  }

  private fun <T, R> Flow<List<T>>.mapEntitiesToDomain(mapper: (T) -> R): Flow<List<R>> {
    return this.map { list ->
      list.map(mapper)
    }.catch { e ->
      e.printStackTrace()
      throw ConversionException(ConversionError.DB_ERROR)
    }
  }
}
