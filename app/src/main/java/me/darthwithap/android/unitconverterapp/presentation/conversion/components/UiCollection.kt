package me.darthwithap.android.unitconverterapp.presentation.conversion.components

import androidx.annotation.DrawableRes
import androidx.compose.ui.graphics.Color
import me.darthwithap.android.unitconverterapp.R
import me.darthwithap.android.unitconverterapp.domain.models.Collection
import me.darthwithap.android.unitconverterapp.presentation.ui.theme.AccelerationColor
import me.darthwithap.android.unitconverterapp.presentation.ui.theme.AreaColor
import me.darthwithap.android.unitconverterapp.presentation.ui.theme.DataColor
import me.darthwithap.android.unitconverterapp.presentation.ui.theme.DensityColor
import me.darthwithap.android.unitconverterapp.presentation.ui.theme.EnergyColor
import me.darthwithap.android.unitconverterapp.presentation.ui.theme.ForceColor
import me.darthwithap.android.unitconverterapp.presentation.ui.theme.LengthColor
import me.darthwithap.android.unitconverterapp.presentation.ui.theme.PowerColor
import me.darthwithap.android.unitconverterapp.presentation.ui.theme.PressureColor
import me.darthwithap.android.unitconverterapp.presentation.ui.theme.SpeedColor
import me.darthwithap.android.unitconverterapp.presentation.ui.theme.TemperatureColor
import me.darthwithap.android.unitconverterapp.presentation.ui.theme.TimeColor
import me.darthwithap.android.unitconverterapp.presentation.ui.theme.VolumeColor
import me.darthwithap.android.unitconverterapp.presentation.ui.theme.WeightColor

data class UiCollection(
    val collection: Collection,
    @DrawableRes val drawableRes: Int,
    val tintColor: Color
) {
  companion object {
    fun byCollection(collection: Collection): UiCollection {
      return when (collection.name) {
        "Length" -> UiCollection(collection, R.drawable.ic_length, LengthColor)
        "Weight" -> UiCollection(collection, R.drawable.ic_weight, WeightColor)
        "Volume" -> UiCollection(collection, R.drawable.ic_volume, VolumeColor)
        "Temperature" -> UiCollection(collection, R.drawable.ic_temperature, TemperatureColor)
        "Speed" -> UiCollection(collection, R.drawable.ic_speed, SpeedColor)
        "Time" -> UiCollection(collection, R.drawable.ic_time, TimeColor)
        "Area" -> UiCollection(collection, R.drawable.ic_area, AreaColor)
        "Pressure" -> UiCollection(collection, R.drawable.ic_pressure, PressureColor)
        "Power" -> UiCollection(collection, R.drawable.ic_power, PowerColor)
        "Acceleration" -> UiCollection(collection, R.drawable.ic_acceleration, AccelerationColor)
        "Density" -> UiCollection(collection, R.drawable.ic_density, DensityColor)
        "Energy" -> UiCollection(collection, R.drawable.ic_energy, EnergyColor)
        "Force" -> UiCollection(collection, R.drawable.ic_force, ForceColor)
        "Data" -> UiCollection(collection, R.drawable.ic_data, DataColor)
        else -> UiCollection(collection, R.drawable.ic_length, LengthColor)
      }
    }
  }
}
