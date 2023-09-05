package me.darthwithap.android.unitconverterapp.domain.models

import me.darthwithap.android.unitconverterapp.data.models.SingleUnitEntity

data class SingleUnit(
    val id: Int,
    val collectionName: String,
    val name: String,
    val symbol: String,
    val multiplier: Double,
    val offset: Double
) {
  fun toEntity(): SingleUnitEntity {
    return SingleUnitEntity(id, collectionName, name, symbol, multiplier, offset)
  }
}
