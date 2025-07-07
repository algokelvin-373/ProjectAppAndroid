package com.algokelvin.shoppingyuk.presentation.di.core

import com.algokelvin.shoppingyuk.data.api.ProductApiService
import com.algokelvin.shoppingyuk.data.repository.cart.datasource.CartRemoteDataSource
import com.algokelvin.shoppingyuk.data.repository.cart.datasourceImpl.CartRemoteDataSourceImpl
import com.algokelvin.shoppingyuk.data.repository.login.datasource.LoginRemoteDataSource
import com.algokelvin.shoppingyuk.data.repository.login.datasourceImpl.LoginRemoteDataSourceImpl
import com.algokelvin.shoppingyuk.data.repository.product.datasource.ProductRemoteDataSource
import com.algokelvin.shoppingyuk.data.repository.product.datasourceImpl.ProductRemoteDataSourceImpl
import com.algokelvin.shoppingyuk.data.repository.productCategory.datasource.ProductCategoryRemoteDataSource
import com.algokelvin.shoppingyuk.data.repository.productCategory.datasourceImpl.ProductCategoryRemoteDataSourceImpl
import com.algokelvin.shoppingyuk.data.repository.productDetail.datasource.ProductDetailRemoteDataSource
import com.algokelvin.shoppingyuk.data.repository.productDetail.datasourceImpl.ProductDetailRemoteDataSourceImpl
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class RemoteDataModule {
    @Singleton
    @Provides
    fun provideMovieRemoteDataModule(productApiService: ProductApiService): ProductRemoteDataSource {
        return ProductRemoteDataSourceImpl(productApiService)
    }

    @Singleton
    @Provides
    fun provideProductDetailRemoteDataModule(productApiService: ProductApiService): ProductDetailRemoteDataSource {
        return ProductDetailRemoteDataSourceImpl(productApiService)
    }

    @Singleton
    @Provides
    fun provideLoginRemoteDataModule(productApiService: ProductApiService): LoginRemoteDataSource {
        return LoginRemoteDataSourceImpl(productApiService)
    }

    @Singleton
    @Provides
    fun provideProductCategoryRemoteDataModule(productApiService: ProductApiService): ProductCategoryRemoteDataSource {
        return ProductCategoryRemoteDataSourceImpl(productApiService)
    }

    @Singleton
    @Provides
    fun provideCartRemoteDataModule(productApiService: ProductApiService): CartRemoteDataSource {
        return CartRemoteDataSourceImpl(productApiService)
    }
}