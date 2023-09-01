package me.darthwithap.android.unitconverterapp.domain.models

data class SingleUnit(
  val id: Int,
  val collection: String,
  val name: String,
  val symbol: String,
  val multiplier: Float,
  val offset: Float
)
