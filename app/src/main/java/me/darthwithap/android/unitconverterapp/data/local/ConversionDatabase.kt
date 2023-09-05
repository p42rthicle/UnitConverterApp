package me.darthwithap.android.unitconverterapp.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import me.darthwithap.android.unitconverterapp.data.models.ConversionEntity
import me.darthwithap.android.unitconverterapp.data.models.ConversionUnitsEntity
import me.darthwithap.android.unitconverterapp.data.models.SingleUnitEntity

@Database(
    entities = [
      ConversionEntity::class,
      ConversionUnitsEntity::class,
      SingleUnitEntity::class
    ],
    version = 1
)
abstract class ConversionDatabase : RoomDatabase() {
  abstract val conversionDao: ConversionDao
}