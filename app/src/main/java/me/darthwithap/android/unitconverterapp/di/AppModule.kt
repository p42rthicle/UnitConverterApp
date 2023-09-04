package me.darthwithap.android.unitconverterapp.di

import android.app.Application
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import me.darthwithap.android.unitconverterapp.data.db.DbFileReader
import me.darthwithap.android.unitconverterapp.data.local.ConversionDatabase
import me.darthwithap.android.unitconverterapp.data.preferences.DefaultPreferences
import me.darthwithap.android.unitconverterapp.data.repository.ConverterRepositoryImpl
import me.darthwithap.android.unitconverterapp.domain.preferences.Preferences
import me.darthwithap.android.unitconverterapp.domain.repository.ConverterRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
  @Provides
  @Singleton
  fun provideSharedPreferences(app: Application): SharedPreferences {
    return app.getSharedPreferences("shared_prefs", MODE_PRIVATE)
  }

  @Provides
  @Singleton
  fun providePreferences(sharedPreferences: SharedPreferences): Preferences {
    return DefaultPreferences(sharedPreferences)
  }

  @Provides
  @Singleton
  fun provideDbReader(app: Application): DbFileReader {
    return DbFileReader(app.applicationContext)
  }

  @Provides
  @Singleton
  fun provideConversionDatabase(app: Application): ConversionDatabase {
    return Room.databaseBuilder(
      app.applicationContext,
      ConversionDatabase::class.java,
      "conversion_db"
    ).build()
  }

  @Provides
  @Singleton
  fun provideConverterRepository(
    dbFileReader: DbFileReader,
    db: ConversionDatabase
  ): ConverterRepository {
    return ConverterRepositoryImpl(dbFileReader, db)
  }
}