package me.darthwithap.android.unitconverterapp.domain.preferences

import me.darthwithap.android.unitconverterapp.domain.models.ConversionUnits

interface Preferences {
  suspend fun saveUserPreferredUnits(units: String)
  suspend fun getUserPreferredUnits(): String

  companion object {
    const val KEY_USER_UNITS_PREFERENCE = "user_units"
  }
}