package com.algokelvin.movieapp.presentation.di.core

import com.algokelvin.movieapp.data.repository.cart.CartRepositoryImpl
import com.algokelvin.movieapp.data.repository.cart.datasource.CartRemoteDataSource
import com.algokelvin.movieapp.data.repository.login.LoginRepositoryImpl
import com.algokelvin.movieapp.data.repository.login.datasource.LoginLocalDataSource
import com.algokelvin.movieapp.data.repository.login.datasource.LoginRemoteDataSource
import com.algokelvin.movieapp.data.repository.product.ProductRepositoryImpl
import com.algokelvin.movieapp.data.repository.product.datasource.ProductCacheDataSource
import com.algokelvin.movieapp.data.repository.product.datasource.ProductLocalDataSource
import com.algokelvin.movieapp.data.repository.product.datasource.ProductRemoteDataSource
import com.algokelvin.movieapp.data.repository.productCategory.ProductCategoryRepositoryImpl
import com.algokelvin.movieapp.data.repository.productCategory.datasource.ProductCategoryRemoteDataSource
import com.algokelvin.movieapp.data.repository.productDetail.ProductDetailRepositoryImpl
import com.algokelvin.movieapp.data.repository.productDetail.datasource.ProductDetailRemoteDataSource
import com.algokelvin.movieapp.domain.repository.CartRepository
import com.algokelvin.movieapp.domain.repository.LoginRepository
import com.algokelvin.movieapp.domain.repository.ProductCategoryRepository
import com.algokelvin.movieapp.domain.repository.ProductDetailRepository
import com.algokelvin.movieapp.domain.repository.ProductRepository
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
        cartRemoteDataSource: CartRemoteDataSource,
        productLocalDataSource: ProductLocalDataSource,
    ): CartRepository {
        return CartRepositoryImpl(
            cartRemoteDataSource,
            productLocalDataSource,
        )
    }
}