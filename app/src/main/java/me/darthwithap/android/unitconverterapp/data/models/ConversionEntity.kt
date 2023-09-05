package me.darthwithap.android.unitconverterapp.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import me.darthwithap.android.unitconverterapp.domain.models.Conversion
import me.darthwithap.android.unitconverterapp.domain.models.SingleUnit

@Entity
data class ConversionEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long,
    val fromUnit: String,
    val toUnit: String,
    val inputValue: Double?,
    val outputValue: Double?,
    val collectionName: String,
    val isFavourite: Boolean = false,
    val timestamp: Long = System.currentTimeMillis()
) {
  fun toDomainModel(
      fromUnit: SingleUnit,
      toUnit: SingleUnit
  ): Conversion {
    return Conversion(
        id, fromUnit, toUnit, inputValue, outputValue, fromUnit.collectionName, isFavourite, timestamp
    )
  }
}