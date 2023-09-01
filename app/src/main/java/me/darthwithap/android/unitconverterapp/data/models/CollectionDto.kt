package me.darthwithap.android.unitconverterapp.data.models

import me.darthwithap.android.unitconverterapp.domain.models.Collection

data class CollectionDto(
  val name: String,
  val units: List<SingleUnitDto>
) {
  fun toDomainModel(): Collection {
    return Collection(
      name, units.map { it.toDomainModel() }
    )
  }
}
