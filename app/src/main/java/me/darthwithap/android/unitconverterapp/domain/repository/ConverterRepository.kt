package me.darthwithap.android.unitconverterapp.domain.repository

import me.darthwithap.android.unitconverterapp.domain.models.ConversionResult

interface ConverterRepository {
  fun convert(): ConversionResult
}