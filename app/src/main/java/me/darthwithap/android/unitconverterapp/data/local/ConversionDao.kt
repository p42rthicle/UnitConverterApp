package me.darthwithap.android.unitconverterapp.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow
import me.darthwithap.android.unitconverterapp.data.models.ConversionEntity
import me.darthwithap.android.unitconverterapp.data.models.ConversionUnitsEntity
import me.darthwithap.android.unitconverterapp.data.models.SingleUnitEntity

@Dao
interface ConversionDao {
  @Upsert
  suspend fun updateConversion(conversion: ConversionEntity): Long
  
  @Query("SELECT * FROM ConversionEntity WHERE isFavourite = 0 ORDER BY timestamp DESC")
  fun getRecentConversions(): Flow<List<ConversionEntity>>
  
  @Query("SELECT * FROM ConversionEntity WHERE isFavourite = 1 ORDER BY timestamp DESC")
  fun getFavouriteConversions(): Flow<List<ConversionEntity>>
  
  @Delete
  suspend fun deleteConversion(conversion: ConversionEntity)
  
  @Upsert
  suspend fun updateConversionUnits(units: ConversionUnitsEntity): Long
  
  @Query("SELECT * FROM ConversionUnitsEntity WHERE isFavourite = 0 ORDER BY timestamp DESC")
  fun getRecentConversionUnits(): Flow<List<ConversionUnitsEntity>>
  
  @Query("SELECT * FROM ConversionUnitsEntity WHERE isFavourite = 1 ORDER BY timestamp DESC")
  fun getFavouriteConversionUnits(): Flow<List<ConversionUnitsEntity>>
  
  @Delete
  suspend fun deleteConversionUnits(units: ConversionUnitsEntity)
  
  @Insert(onConflict = OnConflictStrategy.REPLACE)
  suspend fun addSingleUnits(units: List<SingleUnitEntity>)
  
  @Query("SELECT * FROM SingleUnitEntity")
  fun getAllSingleUnits(): Flow<List<SingleUnitEntity>>
  
  @Query("SELECT * FROM SingleUnitEntity WHERE name = :name")
  fun getSingleUnitByName(name: String): Flow<SingleUnitEntity>
}
