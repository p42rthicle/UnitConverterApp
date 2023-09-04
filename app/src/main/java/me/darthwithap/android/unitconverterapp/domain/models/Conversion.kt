package me.darthwithap.android.unitconverterapp.domain.models

import me.darthwithap.android.unitconverterapp.data.models.ConversionEntity

data class Conversion(
    val id: Long,
    val fromUnit: SingleUnit,
    val toUnit: SingleUnit,
    val inputValue: Double?,
    val outputValue: Double?,
    val collectionName: String,
    val isFavourite: Boolean = false
) {
  fun toEntity(): ConversionEntity {
    return ConversionEntity(
        id, fromUnit.name, toUnit.name, inputValue, outputValue, collectionName, isFavourite
    )
  }
}
