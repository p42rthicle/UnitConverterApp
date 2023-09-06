package me.darthwithap.android.unitconverterapp.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import me.darthwithap.android.unitconverterapp.domain.repository.ConverterRepository
import me.darthwithap.android.unitconverterapp.domain.usecases.AddConversionUnitsUseCase
import me.darthwithap.android.unitconverterapp.domain.usecases.BatchConvertUseCase
import me.darthwithap.android.unitconverterapp.domain.usecases.CollectionsUseCase
import me.darthwithap.android.unitconverterapp.domain.usecases.ConversionUseCases
import me.darthwithap.android.unitconverterapp.domain.usecases.ConvertUseCase
import me.darthwithap.android.unitconverterapp.domain.usecases.DeleteConversionUnitsUseCase
import me.darthwithap.android.unitconverterapp.domain.usecases.DeleteConversionUseCase
import me.darthwithap.android.unitconverterapp.domain.usecases.FavouriteConversionUnitsUseCase
import me.darthwithap.android.unitconverterapp.domain.usecases.FavouriteConversionsUseCase
import me.darthwithap.android.unitconverterapp.domain.usecases.RecentConversionUnitsUseCase
import me.darthwithap.android.unitconverterapp.domain.usecases.RecentConversionsUseCase
import me.darthwithap.android.unitconverterapp.domain.usecases.ToggleFavouriteConversionUnitsUseCase
import me.darthwithap.android.unitconverterapp.domain.usecases.ToggleFavouriteConversionUseCase
import me.darthwithap.android.unitconverterapp.domain.usecases.ValidateInputUseCase

@Module
@InstallIn(ViewModelComponent::class)
object DomainModule {
  @Provides
  @ViewModelScoped
  fun provideConversionUseCases(
      repository: ConverterRepository
  ): ConversionUseCases {
    return ConversionUseCases(
        convert = ConvertUseCase(repository),
        batchConvert = BatchConvertUseCase(),
        getRecentConversions = RecentConversionsUseCase(repository),
        getFavouriteConversions = FavouriteConversionsUseCase(repository),
        toggleFavouriteConversion = ToggleFavouriteConversionUseCase(repository),
        deleteConversion = DeleteConversionUseCase(repository),
        addConversionUnits = AddConversionUnitsUseCase(repository),
        getRecentConversionUnits = RecentConversionUnitsUseCase(repository),
        getFavouriteConversionUnits = FavouriteConversionUnitsUseCase(repository),
        toggleFavouriteConversionUnits = ToggleFavouriteConversionUnitsUseCase(repository),
        deleteConversionUnits = DeleteConversionUnitsUseCase(repository),
        collections = CollectionsUseCase(repository),
        validateInput = ValidateInputUseCase()
    )
  }
}