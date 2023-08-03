package me.darthwithap.android.unitconverterapp.data.models

data class SingleUnitDto(
  val id: Int,
  val collection: String,
  val name: String,
  val symbol: String,
  val multiplier: Float,
  val offset: Float
)
