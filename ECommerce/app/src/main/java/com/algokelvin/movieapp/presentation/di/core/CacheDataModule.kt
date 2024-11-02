package com.algokelvin.movieapp.presentation.di.core

import com.algokelvin.movieapp.data.repository.product.datasource.ProductCacheDataSource
import com.algokelvin.movieapp.data.repository.product.datasourceImpl.ProductCacheDataSourceImpl
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class CacheDataModule {
    @Singleton
    @Provides
    fun provideProductCacheDataModule(): ProductCacheDataSource {
        return ProductCacheDataSourceImpl()
    }
}