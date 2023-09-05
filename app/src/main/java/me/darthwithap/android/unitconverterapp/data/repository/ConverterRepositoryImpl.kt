package me.darthwithap.android.unitconverterapp.data.repository

import android.database.SQLException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import me.darthwithap.android.unitconverterapp.data.db.DbFileReader
import me.darthwithap.android.unitconverterapp.data.local.ConversionDatabase
import me.darthwithap.android.unitconverterapp.data.models.CollectionDto
import me.darthwithap.android.unitconverterapp.domain.models.Collection
import me.darthwithap.android.unitconverterapp.domain.models.Conversion
import me.darthwithap.android.unitconverterapp.domain.models.ConversionUnits
import me.darthwithap.android.unitconverterapp.domain.repository.ConverterRepository
import me.darthwithap.android.unitconverterapp.util.ConversionError
import me.darthwithap.android.unitconverterapp.util.ConversionException

@OptIn(ExperimentalCoroutinesApi::class)
class ConverterRepositoryImpl(
    private val dbFileReader: DbFileReader,
    db: ConversionDatabase
) : ConverterRepository {
  private val dao = db.conversionDao
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
    return dao.getRecentConversions().map { conversationEntities ->
      conversationEntities.map {
        val fromUnit = dao.getSingleUnitByName(it.fromUnit).first().toDomainModel()
        val toUnit = dao.getSingleUnitByName(it.toUnit).first().toDomainModel()
        it.toDomainModel(fromUnit, toUnit)
      }
    }.catch { e ->
      e.printStackTrace()
      throw ConversionException(ConversionError.DB_ERROR)
    }
  }
  
  override fun getFavouriteConversions(): Flow<List<Conversion>> {
    return dao.getFavouriteConversions().map { conversationEntities ->
      conversationEntities.map {
        val fromUnit = dao.getSingleUnitByName(it.fromUnit).first().toDomainModel()
        val toUnit = dao.getSingleUnitByName(it.toUnit).first().toDomainModel()
        it.toDomainModel(fromUnit, toUnit)
      }
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
    return dao.getRecentConversionUnits().map { unitsEntities ->
      unitsEntities.map {
        val fromUnit = dao.getSingleUnitByName(it.fromUnit).first().toDomainModel()
        val toUnit = dao.getSingleUnitByName(it.toUnit).first().toDomainModel()
        it.toDomainModel(fromUnit, toUnit)
      }
    }.catch { e ->
      e.printStackTrace()
      throw ConversionException(ConversionError.DB_ERROR)
    }
  }
  
  override fun getRecentConversionUnitsByUnits(
      fromUnit: String,
      toUnit: String
  ): Flow<ConversionUnits?> {
    return dao.getRecentConversionUnitsByUnits(fromUnit, toUnit).map { unitEntity ->
      unitEntity?.let {
        val fromSingleUnit = dao.getSingleUnitByName(it.fromUnit).first().toDomainModel()
        val toSingleUnit = dao.getSingleUnitByName(it.toUnit).first().toDomainModel()
        it.toDomainModel(fromSingleUnit, toSingleUnit)
      }
    }.catch { e ->
      e.printStackTrace()
      throw ConversionException(ConversionError.DB_ERROR)
    }
  }
  
  override fun getFavouriteConversionUnits(): Flow<List<ConversionUnits>> {
    return dao.getFavouriteConversionUnits().map { unitsEntities ->
      unitsEntities.map {
        val fromUnit = dao.getSingleUnitByName(it.fromUnit).first().toDomainModel()
        val toUnit = dao.getSingleUnitByName(it.toUnit).first().toDomainModel()
        it.toDomainModel(fromUnit, toUnit)
      }
    }.catch { e ->
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
    return dao.getAllSingleUnits().flatMapLatest { units ->
      if (units.isEmpty()) {
        val collections = getCollectionsFromFile()
        
        dao.addSingleUnits(collections.flatMap { collection ->
          collection.units.map { it.toEntity() }
        })
        
        flowOf(collections.map { it.toDomainModel() })
      } else {
        val collectionsFromCache = units.groupBy { it.collectionName }
            .map { (collectionName, units) ->
              Collection(collectionName, units.map { it.toDomainModel() })
            }
        flowOf(collectionsFromCache)
      }
    }.flowOn(Dispatchers.IO)
  }
  
  private fun getCollectionsFromFile(): List<CollectionDto> {
    return dbFileReader.getAllCollections()
  }
}
