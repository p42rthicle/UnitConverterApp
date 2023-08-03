package me.darthwithap.android.unitconverterapp.domain.usecases

import me.darthwithap.android.unitconverterapp.domain.models.ConversionResult
import me.darthwithap.android.unitconverterapp.domain.repository.ConverterRepository

class ConverterUseCase constructor(
  private val repository: ConverterRepository
) {
  operator fun invoke(): ConversionResult {
    return repository.convert()
  }
}