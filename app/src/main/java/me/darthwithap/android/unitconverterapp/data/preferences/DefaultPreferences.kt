package me.darthwithap.android.unitconverterapp.data.preferences

import android.content.SharedPreferences
import me.darthwithap.android.unitconverterapp.domain.preferences.Preferences

class DefaultPreferences(
    private val sharedPrefs: SharedPreferences
) : Preferences {
  override suspend fun saveUserPreferredUnits(units: String) {
    sharedPrefs.edit().putString(Preferences.KEY_USER_UNITS_PREFERENCE, units).apply()
  }
  
  override suspend fun getUserPreferredUnits(): String {
    return sharedPrefs.getString(Preferences.KEY_USER_UNITS_PREFERENCE, null) ?: "Default"
  }
}