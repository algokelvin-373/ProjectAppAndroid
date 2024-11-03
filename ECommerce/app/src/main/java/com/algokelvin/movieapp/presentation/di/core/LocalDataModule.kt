package com.algokelvin.movieapp.presentation.di.core

import com.algokelvin.movieapp.data.db.CartDao
import com.algokelvin.movieapp.data.db.ProductDao
import com.algokelvin.movieapp.data.db.UserDao
import com.algokelvin.movieapp.data.repository.cart.datasource.CartLocalDataSource
import com.algokelvin.movieapp.data.repository.cart.datasourceImpl.CartLocalDataSourceImpl
import com.algokelvin.movieapp.data.repository.login.datasource.LoginLocalDataSource
import com.algokelvin.movieapp.data.repository.login.datasourceImpl.LoginLocalDataSourceImpl
import com.algokelvin.movieapp.data.repository.product.datasource.ProductLocalDataSource
import com.algokelvin.movieapp.data.repository.product.datasourceImpl.ProductLocalDataSourceImpl
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class LocalDataModule {
    @Singleton
    @Provides
    fun provideMovieLocalDataModule(productDao: ProductDao): ProductLocalDataSource {
        return ProductLocalDataSourceImpl(productDao)
    }

    @Singleton
    @Provides
    fun provideLoginLocalDataModule(userDao: UserDao): LoginLocalDataSource {
        return LoginLocalDataSourceImpl(userDao)
    }

    @Singleton
    @Provides
    fun provideCartLocalDataModule(cartDao: CartDao): CartLocalDataSource {
        return CartLocalDataSourceImpl(cartDao)
    }
}