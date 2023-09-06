package me.darthwithap.android.unitconverterapp.domain.usecases

data class ConversionUseCases(
    val convert: ConvertUseCase,
    val batchConvert: BatchConvertUseCase,
    val getRecentConversions: RecentConversionsUseCase,
    val getFavouriteConversions: FavouriteConversionsUseCase,
    val toggleFavouriteConversion: ToggleFavouriteConversionUseCase,
    val deleteConversion: DeleteConversionUseCase,
    val addConversionUnits: AddConversionUnitsUseCase,
    val getRecentConversionUnits: RecentConversionUnitsUseCase,
    val getFavouriteConversionUnits: FavouriteConversionUnitsUseCase,
    val toggleFavouriteConversionUnits: ToggleFavouriteConversionUnitsUseCase,
    val deleteConversionUnits: DeleteConversionUnitsUseCase,
    val collections: CollectionsUseCase,
    val validateInput: ValidateInputUseCase
)
