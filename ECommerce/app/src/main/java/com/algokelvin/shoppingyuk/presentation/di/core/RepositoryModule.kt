package com.algokelvin.shoppingyuk.presentation.di.core

import com.algokelvin.shoppingyuk.data.repository.cart.CartRepositoryImpl
import com.algokelvin.shoppingyuk.data.repository.cart.datasource.CartLocalDataSource
import com.algokelvin.shoppingyuk.data.repository.login.LoginRepositoryImpl
import com.algokelvin.shoppingyuk.data.repository.login.datasource.LoginLocalDataSource
import com.algokelvin.shoppingyuk.data.repository.login.datasource.LoginRemoteDataSource
import com.algokelvin.shoppingyuk.data.repository.product.ProductRepositoryImpl
import com.algokelvin.shoppingyuk.data.repository.product.datasource.ProductCacheDataSource
import com.algokelvin.shoppingyuk.data.repository.product.datasource.ProductLocalDataSource
import com.algokelvin.shoppingyuk.data.repository.product.datasource.ProductRemoteDataSource
import com.algokelvin.shoppingyuk.data.repository.productCategory.ProductCategoryRepositoryImpl
import com.algokelvin.shoppingyuk.data.repository.productCategory.datasource.ProductCategoryRemoteDataSource
import com.algokelvin.shoppingyuk.data.repository.productDetail.ProductDetailRepositoryImpl
import com.algokelvin.shoppingyuk.data.repository.productDetail.datasource.ProductDetailRemoteDataSource
import com.algokelvin.shoppingyuk.domain.repository.CartRepository
import com.algokelvin.shoppingyuk.domain.repository.LoginRepository
import com.algokelvin.shoppingyuk.domain.repository.ProductCategoryRepository
import com.algokelvin.shoppingyuk.domain.repository.ProductDetailRepository
import com.algokelvin.shoppingyuk.domain.repository.ProductRepository
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class RepositoryModule {
    @Provides
    @Singleton
    fun provideMovieRepository(
        productRemoteDataSource: ProductRemoteDataSource,
        productLocalDataSource: ProductLocalDataSource,
        productCacheDataSource: ProductCacheDataSource,
    ): ProductRepository {
        return ProductRepositoryImpl(
            productRemoteDataSource,
            productLocalDataSource,
            productCacheDataSource
        )
    }

    @Provides
    @Singleton
    fun provideProductDetailRepository(
        productDetailRemoteDataSource: ProductDetailRemoteDataSource
    ): ProductDetailRepository {
        return ProductDetailRepositoryImpl(
            productDetailRemoteDataSource
        )
    }

    @Provides
    @Singleton
    fun provideLoginRepository(
        loginRemoteDataSource: LoginRemoteDataSource,
        loginLocalDataSource: LoginLocalDataSource,
    ): LoginRepository {
        return LoginRepositoryImpl(
            loginRemoteDataSource,
            loginLocalDataSource
        )
    }

    @Provides
    @Singleton
    fun provideProductCategory(
        productCategoryRemoteDataSource: ProductCategoryRemoteDataSource
    ): ProductCategoryRepository {
        return ProductCategoryRepositoryImpl(
            productCategoryRemoteDataSource
        )
    }

    @Provides
    @Singleton
    fun provideCartRepository(
        cartLocalDataSource: CartLocalDataSource
    ): CartRepository {
        return CartRepositoryImpl(
            cartLocalDataSource
        )
    }
}