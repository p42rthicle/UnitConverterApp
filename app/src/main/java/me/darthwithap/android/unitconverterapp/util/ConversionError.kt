package me.darthwithap.android.unitconverterapp.util

enum class ConversionError(error: String) {
  DB_ERROR("Error with database"),
  INVALID_INPUT_VALUE("Invalid input value"),
  DIFFERENT_COLLECTIONS("Can't convert to a different type"),
  GENERAL_ERROR("Some Error Occurred"),
  INVALID_CHARACTERS_FOUND("Invalid characters found."),
  INVALID_DECIMAL_POSITION("Decimal point cannot be at the end."),
  ONLY_DECIMAL_INVALID("Just a decimal point isn't valid."),
  NEGATIVE_VALUES_NOT_ALLOWED("Cannot have negative values.")
  
  
}

class ConversionException(val error: ConversionError) : Exception(error.name)