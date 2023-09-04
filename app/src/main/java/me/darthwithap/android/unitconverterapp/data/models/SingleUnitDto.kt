package me.darthwithap.android.unitconverterapp.data.models

import me.darthwithap.android.unitconverterapp.domain.models.SingleUnit

data class SingleUnitDto(
    val id: Int,
    val collection: String,
    val name: String,
    val symbol: String,
    val multiplier: Double,
    val offset: Double
) {
  fun toDomainModel(): SingleUnit {
    return SingleUnit(id, collection, name, symbol, multiplier, offset)
  }
  
  fun toEntity(): SingleUnitEntity {
    return SingleUnitEntity(id, collection, name, symbol, multiplier, offset)
  }
}
