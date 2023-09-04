package me.darthwithap.android.unitconverterapp.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import me.darthwithap.android.unitconverterapp.domain.models.SingleUnit

@Entity
data class SingleUnitEntity(
  @PrimaryKey(autoGenerate = true)
  val id: Int,
  val collectionName: String,
  val name: String,
  val symbol: String,
  val multiplier: Double,
  val offset: Double
) {
  fun toDomainModel(): SingleUnit {
    return SingleUnit(id, collectionName, name, symbol, multiplier, offset)
  }
}