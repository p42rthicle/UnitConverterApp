package me.darthwithap.android.unitconverterapp.presentation.conversion

import me.darthwithap.android.unitconverterapp.domain.models.Conversion
import me.darthwithap.android.unitconverterapp.domain.models.ConversionUnits
import me.darthwithap.android.unitconverterapp.domain.models.SingleUnit
import me.darthwithap.android.unitconverterapp.presentation.conversion.components.UiCollection
import me.darthwithap.android.unitconverterapp.util.ConversionError

data class ConversionState(
    val fromUnit: SingleUnit? = null,
    val toUnit: SingleUnit? = null,
    val inputValue: String = "0",
    val outputValue: String? = null,
    val isConverting: Boolean = false,
    val isChoosingFromUnit: Boolean = false,
    val isChoosingToUnit: Boolean = false,
    val isChoosingCollection: Boolean = false,
    val isBatchConversion: Boolean = false,
    val isShowingHistory: Boolean = false,
    val currentCollection: UiCollection? = null,
    val collections: List<UiCollection> = emptyList(),
    val recentConversions: List<Conversion> = emptyList(),
    val favouriteConversions: List<Conversion> = emptyList(),
    val recentConversionUnits: List<ConversionUnits> = emptyList(),
    val favouriteConversionUnits: List<ConversionUnits> = emptyList(),
    val batchConversionResult: Map<SingleUnit, String> = emptyMap(),
    val error: ConversionError? = null
)
