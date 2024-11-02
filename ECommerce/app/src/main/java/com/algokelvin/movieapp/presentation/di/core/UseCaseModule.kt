package com.algokelvin.movieapp.presentation.di.core

import com.algokelvin.movieapp.domain.repository.LoginRepository
import com.algokelvin.movieapp.domain.repository.ProductCategoryRepository
import com.algokelvin.movieapp.domain.repository.ProductDetailRepository
import com.algokelvin.movieapp.domain.repository.ProductRepository
import com.algokelvin.movieapp.domain.usecase.GetProductDetailUseCase
import com.algokelvin.movieapp.domain.usecase.GetProductsCategoryUseCase
import com.algokelvin.movieapp.domain.usecase.GetProductsUseCase
import com.algokelvin.movieapp.domain.usecase.LoginUseCase
import dagger.Module
import dagger.Provides

@Module
class UseCaseModule {
    @Provides
    fun provideGetMovieUseCase(productRepository: ProductRepository): GetProductsUseCase {
        return GetProductsUseCase(productRepository)
    }

    @Provides
    fun provideGetProductDetailUseCase(productDetailRepository: ProductDetailRepository): GetProductDetailUseCase {
        return GetProductDetailUseCase(productDetailRepository)
    }

    @Provides
    fun provideLoginUseCase(loginRepository: LoginRepository): LoginUseCase {
        return LoginUseCase(loginRepository)
    }

    @Provides
    fun provideGetProductsCategoryUseCase(productCategoryRepository: ProductCategoryRepository): GetProductsCategoryUseCase {
        return GetProductsCategoryUseCase(productCategoryRepository)
    }
}