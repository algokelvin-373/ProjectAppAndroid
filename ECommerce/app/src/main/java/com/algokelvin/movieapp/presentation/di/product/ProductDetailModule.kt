package com.algokelvin.movieapp.presentation.di.product

import com.algokelvin.movieapp.domain.usecase.GetProductDetailUseCase
import com.algokelvin.movieapp.presentation.productdetail.ProductDetailViewModelFactory
import dagger.Module
import dagger.Provides

@Module
class ProductDetailModule {
    @ProductDetailScope
    @Provides
    fun provideProductDetailViewModelFactory(
        getProductDetailUseCase: GetProductDetailUseCase,
    ): ProductDetailViewModelFactory = ProductDetailViewModelFactory(getProductDetailUseCase)
}