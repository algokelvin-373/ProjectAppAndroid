package com.algokelvin.movieapp.presentation.di.product

import com.algokelvin.movieapp.domain.usecase.GetProductsUseCase
import com.algokelvin.movieapp.presentation.product.ProductViewModelFactory
import dagger.Module
import dagger.Provides

@Module
class ProductModule {
    @ProductScope
    @Provides
    fun provideMovieViewModelFactory(
        getProductsUseCase: GetProductsUseCase,
    ): ProductViewModelFactory = ProductViewModelFactory(getProductsUseCase)
}