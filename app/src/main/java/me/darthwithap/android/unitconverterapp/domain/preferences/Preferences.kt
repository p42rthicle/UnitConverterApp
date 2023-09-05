package me.darthwithap.android.unitconverterapp.domain.preferences

interface Preferences {
  suspend fun saveUserPreferredUnits(units: String)
  suspend fun getUserPreferredUnits(): String
  
  companion object {
    const val KEY_USER_UNITS_PREFERENCE = "user_units"
  }
}