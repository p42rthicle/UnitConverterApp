package me.darthwithap.android.unitconverterapp.util

sealed class ConversionResult<T>(
  val data: T? = null, val error: ConversionException? = null
) {
  class Success<T>(data: T) : ConversionResult<T>(data)
  class Error<T>(e: ConversionException) : ConversionResult<T>(error = e)
}
