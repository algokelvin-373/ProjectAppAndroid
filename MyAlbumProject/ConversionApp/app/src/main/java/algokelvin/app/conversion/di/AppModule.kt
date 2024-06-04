package algokelvin.app.conversion.di

import algokelvin.app.conversion.db.ConverterDatabase
import algokelvin.app.conversion.repository.ConverterDatabaseRepository
import algokelvin.app.conversion.repository.ConverterDatabaseRepositoryImpl
import android.app.Application
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideConverterDatabase(app: Application): ConverterDatabase {
        return Room.databaseBuilder(
            app,
            ConverterDatabase::class.java,
            "result_database"
        ).build()
    }

    @Provides
    @Singleton
    fun provideConverterRepository(db: ConverterDatabase): ConverterDatabaseRepository {
        return ConverterDatabaseRepositoryImpl(db.converterDao)
    }

}