package me.darthwithap.android.unitconverterapp.data.db

import android.content.Context
import me.darthwithap.android.unitconverterapp.data.models.CollectionDto
import me.darthwithap.android.unitconverterapp.data.models.SingleUnitDto
import me.darthwithap.android.unitconverterapp.util.UNITS_DB_FILE_NAME

class DbFileReader(
    private val context: Context
) {
  private val collections = mutableListOf<CollectionDto>()
  
  fun getAllCollections(): List<CollectionDto> {
    try {
      val inputStream = context.assets.open(UNITS_DB_FILE_NAME)
      val bufferedReader = inputStream.bufferedReader()
      
      var currentCollection: CollectionDto? = null
      val currentCollectionUnits = mutableListOf<SingleUnitDto>()
      
      bufferedReader.forEachLine { line ->
        if (line.startsWith("*")) {
          currentCollection?.let {
            collections.add(it.copy(units = currentCollectionUnits.toList()))
            currentCollectionUnits.clear()
          }
          currentCollection = CollectionDto(
              name = line.trim().substring(1).trim(),
              units = emptyList()
          )
        } else {
          val parts = line.split(",").map { it.trim() }
          if (parts.size == 5) {
            currentCollectionUnits.add(
                SingleUnitDto(
                    parts[0].toInt(),
                    currentCollection?.name ?: "",
                    parts[1],
                    parts[2],
                    parts[3].toDouble(),
                    parts[4].toDouble()
                )
            )
          }
        }
      }
      
      // for the last collection in the file
      currentCollection?.let {
        collections.add(it.copy(units = currentCollectionUnits.toList()))
      }
      
      bufferedReader.close()
      inputStream.close()
      
    } catch (e: Exception) {
      e.printStackTrace()
    }
    
    return collections
  }
}