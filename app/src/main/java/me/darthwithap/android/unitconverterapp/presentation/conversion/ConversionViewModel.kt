package me.darthwithap.android.unitconverterapp.presentation.conversion

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import me.darthwithap.android.unitconverterapp.domain.models.SingleUnit
import me.darthwithap.android.unitconverterapp.domain.preferences.Preferences
import me.darthwithap.android.unitconverterapp.domain.usecases.ConversionUseCases
import me.darthwithap.android.unitconverterapp.presentation.conversion.components.UiCollection
import me.darthwithap.android.unitconverterapp.util.ConversionError
import me.darthwithap.android.unitconverterapp.util.ConversionResult
import javax.inject.Inject

@HiltViewModel
class ConversionViewModel @Inject constructor(
    private val conversions: ConversionUseCases,
    prefs: Preferences
) : ViewModel() {
  var state by mutableStateOf(ConversionState())
    private set
  
  private var allUnits: List<SingleUnit> = emptyList()
  private var convertJob: Job? = null
  
  private val TAG = "ConverterAppLogs"
  
  init {
    loadCollections()
    loadConversions()
    loadConversionUnits()
  }
  
  fun onEvent(event: ConversionEvent) {
    when (event) {
      ConversionEvent.ChoosingCollection -> {
        state = state.copy(
            isChoosingCollection = true
        )
      }
      
      ConversionEvent.ChoosingFromUnit -> {
        state = state.copy(
            isChoosingFromUnit = true
        )
      }
      
      ConversionEvent.ChoosingToUnit -> {
        state = state.copy(
            isChoosingToUnit = true
        )
      }
      
      is ConversionEvent.ChosenCollection -> {
        state = state.copy(
            currentCollection = UiCollection.byCollection(event.collection),
            fromUnit = event.collection.units.first(),
            toUnit = event.collection.units.last(),
            isChoosingCollection = false
        )
        convert(isBatchConversion = state.isBatchConversion)
      }
      
      is ConversionEvent.ChosenConversion -> {
        state = state.copy(
            fromUnit = event.conversion.fromUnit,
            toUnit = event.conversion.toUnit,
            inputValue = event.conversion.inputValue.toString(),
            outputValue = event.conversion.outputValue.toString(),
            currentCollection = state.collections.find {
              it.collection.name == event.conversion.collectionName
            }
        )
      }
      
      is ConversionEvent.ChosenConversionUnits -> {
        state = state.copy(
            fromUnit = event.units.fromUnit,
            toUnit = event.units.toUnit,
            currentCollection = state.collections.find {
              it.collection.name == event.units.collection
            }
        )
        convert(false)
      }
      
      is ConversionEvent.ChosenFromUnit -> {
        state = state.copy(
            fromUnit = event.unit,
            isChoosingFromUnit = false
        )
        convert(event.isBatchConversion)
      }
      
      is ConversionEvent.ChosenToUnit -> {
        state = state.copy(
            toUnit = event.unit,
            isChoosingToUnit = false
        )
        convert(isBatchConversion = state.isBatchConversion)
      }
      
      is ConversionEvent.Convert -> {
        convert(isBatchConversion = state.isBatchConversion, saveToHistory = event.saveToHistory)
      }
      
      is ConversionEvent.InputValueChanged -> {
        state = state.copy(
            inputValue = event.value
        )
        if (event.value.isBlank()) {
          onEvent(ConversionEvent.ResetConversion)
          return
        }
        convert(event.isBatchConversion)
      }
      
      ConversionEvent.StoppedChoosingCollection -> {
        state = state.copy(
            isChoosingCollection = false
        )
      }
      
      ConversionEvent.StoppedChoosingFromUnit -> {
        state = state.copy(
            isChoosingFromUnit = false
        )
      }
      
      ConversionEvent.StoppedChoosingToUnit -> {
        state = state.copy(
            isChoosingToUnit = false
        )
      }
      
      is ConversionEvent.ToggleFavouriteConversion -> {
        viewModelScope.launch {
          when (val result = conversions.toggleFavouriteConversion(event.conversion)) {
            is ConversionResult.Error -> {
              state = state.copy(error = result.error?.error)
            }
            
            is ConversionResult.Success -> {
              loadConversions()
            }
          }
        }
      }
      
      is ConversionEvent.ToggleFavouriteConversionUnits -> {
        viewModelScope.launch {
          when (val result = conversions.toggleFavouriteConversionUnits(event.units)) {
            is ConversionResult.Error -> {
              state = state.copy(error = result.error?.error)
            }
            
            is ConversionResult.Success -> {
              loadConversionUnits()
            }
          }
        }
      }
      
      is ConversionEvent.OnErrorSeen -> {
        state = state.copy(
            error = null
        )
      }
      
      is ConversionEvent.DeleteConversion -> {
        viewModelScope.launch {
          when (val result = conversions.deleteConversion(event.conversion)) {
            is ConversionResult.Error -> {
              state = state.copy(error = result.error?.error)
            }
            
            is ConversionResult.Success -> {
              loadConversions()
            }
          }
        }
      }
      
      is ConversionEvent.DeleteConversionUnits -> {
        viewModelScope.launch {
          when (val result = conversions.deleteConversionUnits(event.units)) {
            is ConversionResult.Error -> {
              state = state.copy(error = result.error?.error)
            }
            
            is ConversionResult.Success -> {
              loadConversionUnits()
            }
          }
        }
      }
      
      ConversionEvent.ResetConversion -> {
        state = state.copy(
            inputValue = "",
            outputValue = null,
            batchConversionResult = emptyMap()
        )
      }
      
      ConversionEvent.ToggleBatchConversion -> {
        state = state.copy(
            isBatchConversion = !state.isBatchConversion
        )
        convert(isBatchConversion = state.isBatchConversion)
      }
      
      is ConversionEvent.ChosenBatchConversionFromUnit -> {
        state = state.copy(
            fromUnit = event.unit,
            isChoosingFromUnit = false
        )
        convert(isBatchConversion = state.isBatchConversion)
      }
      
      is ConversionEvent.PerformBatchConversion -> {
        convert(true)
      }
      
      ConversionEvent.SwapUnits -> {
        val fromUnit = state.toUnit
        val toUnit = state.fromUnit
        state = state.copy(
            fromUnit = fromUnit,
            toUnit = toUnit,
        )
        convert(isBatchConversion = false)
      }
    }
  }
  
  private fun loadCollections() {
    viewModelScope.launch {
      handleUseCaseResult(conversions.collections(), onError = {}) { collections ->
        val uiCollections = collections.map { UiCollection.byCollection(it) }
        state = state.copy(
            collections = uiCollections,
            currentCollection = uiCollections.first(),
            fromUnit = collections.first().units.first(),
            toUnit = collections.first().units.last()
        )
        allUnits = collections.flatMap { it.units }
      }
    }
  }
  
  private fun loadConversions() {
    loadRecentConversions()
    loadFavouriteConversions()
  }
  
  private fun loadConversionUnits() {
    loadRecentConversionUnits()
    loadFavouriteConversionUnits()
  }
  
  private fun loadRecentConversions() {
    viewModelScope.launch {
      handleUseCaseResult(conversions.getRecentConversions(), onError = {}) {
        state = state.copy(
            recentConversions = it
        )
      }
    }
  }
  
  private fun loadFavouriteConversions() {
    viewModelScope.launch {
      handleUseCaseResult(conversions.getFavouriteConversions(), onError = {
      }) {
        state = state.copy(
            favouriteConversions = it
        )
      }
    }
  }
  
  private fun loadRecentConversionUnits() {
    viewModelScope.launch {
      handleUseCaseResult(conversions.getRecentConversionUnits(), onError = {}) {
        state = state.copy(
            recentConversionUnits = it
        )
      }
    }
  }
  
  private fun loadFavouriteConversionUnits() {
    viewModelScope.launch {
      handleUseCaseResult(conversions.getFavouriteConversionUnits(), onError = {}) {
        state = state.copy(
            favouriteConversionUnits = it
        )
      }
    }
  }
  
  private fun convert(isBatchConversion: Boolean = false, saveToHistory: Boolean = false) {
    if (state.isConverting || state.inputValue.isBlank()) {
      return
    }
    state = state.copy(isConverting = true)
    val fromUnit = state.fromUnit ?: allUnits.first()
    val toUnit = state.toUnit ?: allUnits.first()
    convertJob = viewModelScope.launch {
      state = state.copy(isConverting = true)
      if (!isBatchConversion) {
        handleUseCaseResult(conversions.convert(
            state.inputValue,
            fromUnit,
            toUnit,
            saveToHistory
        ), onError = {
          state = state.copy(isConverting = false)
        }
        ) { // ON SUCCESS OF CONVERT USE CASE
          state = state.copy(
              isConverting = false,
              outputValue = it.outputValue
          )
          // Add simple conversions to db
          addConversionUnits(fromUnit, toUnit)
        }
      } else {
        handleUseCaseResult(conversions.batchConvert(
            state.inputValue,
            fromUnit,
            state.currentCollection?.collection?.units
        ), onError = {
          state = state.copy(isConverting = false)
        }) {
          state = state.copy(
              isConverting = false,
              batchConversionResult = it
          )
        }
      }
    }
  }
  
  private suspend fun addConversionUnits(fromUnit: SingleUnit, toUnit: SingleUnit) {
    when (val addUnitsResult = conversions.addConversionUnits(fromUnit, toUnit)) {
      is ConversionResult.Error -> {
        state = state.copy(error = addUnitsResult.error?.error)
      }
      
      is ConversionResult.Success -> {
        loadConversionUnits()
      }
    }
  }
  
  private suspend fun <T> handleUseCaseResult(
      useCaseFlow: Flow<ConversionResult<T>>,
      onError: (ConversionError?) -> Unit,
      onSuccess: suspend (T) -> Unit
  ) {
    useCaseFlow.collect { result ->
      when (result) {
        
        is ConversionResult.Success -> {
          onSuccess(result.data!!)
        }
        
        is ConversionResult.Error -> {
          onError(result.error?.error)
          state = state.copy(error = result.error?.error)
        }
      }
    }
  }
}