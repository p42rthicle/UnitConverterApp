package me.darthwithap.android.unitconverterapp.domain.models

data class ConversionResult(
  val fromUnit: SingleUnit,
  val toUnit: SingleUnit,
  val inputValue: Float,
  val outputValue: Float
)
