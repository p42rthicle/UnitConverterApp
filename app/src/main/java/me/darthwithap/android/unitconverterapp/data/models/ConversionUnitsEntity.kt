package me.darthwithap.android.unitconverterapp.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import me.darthwithap.android.unitconverterapp.domain.models.ConversionUnits
import me.darthwithap.android.unitconverterapp.domain.models.SingleUnit

@Entity
data class ConversionUnitsEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val fromUnit: String,
    val toUnit: String,
    val collection: String,
    val isFavourite: Boolean = false,
    val timestamp: Long = System.currentTimeMillis()
) {
  fun toDomainModel(
      fromUnit: SingleUnit,
      toUnit: SingleUnit
  ): ConversionUnits {
    return ConversionUnits(
        id, fromUnit, toUnit, collection, isFavourite, timestamp
    )
  }
}
