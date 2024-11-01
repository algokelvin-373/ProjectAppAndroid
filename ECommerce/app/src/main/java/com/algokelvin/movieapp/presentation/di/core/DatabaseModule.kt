package com.algokelvin.movieapp.presentation.di.core

import android.content.Context
import androidx.room.Room
import com.algokelvin.movieapp.data.db.ArtistDao
import com.algokelvin.movieapp.data.db.ProductDB
import com.algokelvin.movieapp.data.db.ProductDao
import com.algokelvin.movieapp.data.db.TvShowDao
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DatabaseModule {
    @Singleton
    @Provides
    fun provideProductDatabase(context: Context): ProductDB {
        return Room.databaseBuilder(context, ProductDB::class.java, "fakeproductdb")
            .build()
    }

    @Singleton
    @Provides
    fun provideProductDao(productDB: ProductDB): ProductDao {
        return productDB.productDao()
    }

    @Singleton
    @Provides
    fun provideTvShowDao(productDB: ProductDB): TvShowDao {
        return productDB.tvShowDao()
    }

    @Singleton
    @Provides
    fun provideArtistDao(productDB: ProductDB): ArtistDao {
        return productDB.artistDao()
    }
}