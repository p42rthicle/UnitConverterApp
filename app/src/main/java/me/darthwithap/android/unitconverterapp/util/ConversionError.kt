package me.darthwithap.android.unitconverterapp.util

enum class ConversionError(error: String) {
  DB_ERROR("Error with database"),
  INVALID_INPUT_VALUE("Invalid input value"),
  DIFFERENT_COLLECTIONS("Can't convert to a different type"),
  GENERAL_ERROR("Some Error Occurred")
}

class ConversionException(error: ConversionError) : Exception(error.name)