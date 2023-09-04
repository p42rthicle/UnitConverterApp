package me.darthwithap.android.unitconverterapp.domain.models

data class SingleUnit(
    val id: Int,
    val collectionName: String,
    val name: String,
    val symbol: String,
    val multiplier: Double,
    val offset: Double
)
