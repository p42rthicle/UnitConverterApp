package me.darthwithap.android.unitconverterapp.domain.repository

import kotlinx.coroutines.flow.Flow
import me.darthwithap.android.unitconverterapp.domain.models.Collection
import me.darthwithap.android.unitconverterapp.domain.models.Conversion
import me.darthwithap.android.unitconverterapp.domain.models.ConversionUnits

interface ConverterRepository {
  suspend fun updateConversion(conversion: Conversion): Long
  fun getRecentConversions(): Flow<List<Conversion>>
  fun getFavouriteConversions(): Flow<List<Conversion>>
  suspend fun deleteConversion(conversion: Conversion)
  suspend fun updateConversionUnits(units: ConversionUnits): Long
  fun getRecentConversionUnits(): Flow<List<ConversionUnits>>
  fun getFavouriteConversionUnits(): Flow<List<ConversionUnits>>
  suspend fun deleteConversionUnits(units: ConversionUnits)
  suspend fun getCollections(): Flow<List<Collection>>
}