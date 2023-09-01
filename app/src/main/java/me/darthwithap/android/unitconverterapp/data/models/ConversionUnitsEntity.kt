package me.darthwithap.android.unitconverterapp.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import me.darthwithap.android.unitconverterapp.domain.models.ConversionUnits

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
  fun toDomainModel() : ConversionUnits {
    return ConversionUnits(
      id, fromUnit, toUnit, collection
    )
  }
}
