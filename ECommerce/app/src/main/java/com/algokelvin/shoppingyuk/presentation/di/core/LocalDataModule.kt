package com.algokelvin.shoppingyuk.presentation.di.core

import com.algokelvin.shoppingyuk.data.db.CartDao
import com.algokelvin.shoppingyuk.data.db.ProductDao
import com.algokelvin.shoppingyuk.data.db.UserDao
import com.algokelvin.shoppingyuk.data.repository.cart.datasource.CartLocalDataSource
import com.algokelvin.shoppingyuk.data.repository.cart.datasourceImpl.CartLocalDataSourceImpl
import com.algokelvin.shoppingyuk.data.repository.login.datasource.LoginLocalDataSource
import com.algokelvin.shoppingyuk.data.repository.login.datasourceImpl.LoginLocalDataSourceImpl
import com.algokelvin.shoppingyuk.data.repository.product.datasource.ProductLocalDataSource
import com.algokelvin.shoppingyuk.data.repository.product.datasourceImpl.ProductLocalDataSourceImpl
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