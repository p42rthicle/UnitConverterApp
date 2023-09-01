package me.darthwithap.android.unitconverterapp.domain.models

import me.darthwithap.android.unitconverterapp.data.models.ConversionUnitsEntity

data class ConversionUnits(
  val id: Long = 0,
  val fromUnit: String,
  val toUnit: String,
  val collection: String,
  val isFavourite: Boolean = false
) {
  fun toEntity(): ConversionUnitsEntity {
    return ConversionUnitsEntity(
      id, fromUnit, toUnit, collection, isFavourite
    )
  }
}