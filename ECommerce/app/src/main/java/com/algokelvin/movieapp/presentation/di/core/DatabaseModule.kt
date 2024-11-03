package com.algokelvin.movieapp.presentation.di.core

import android.content.Context
import androidx.room.Room
import com.algokelvin.movieapp.data.db.CartDao
import com.algokelvin.movieapp.data.db.ProductDB
import com.algokelvin.movieapp.data.db.ProductDao
import com.algokelvin.movieapp.data.db.UserDao
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
    fun provideUserDao(productDB: ProductDB): UserDao {
        return productDB.userDao()
    }

    @Singleton
    @Provides
    fun provideCartDao(productDB: ProductDB): CartDao {
        return productDB.cartDao()
    }
}