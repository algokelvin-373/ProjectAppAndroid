package com.algokelvin.movieapp.presentation.di.movie

import com.algokelvin.movieapp.domain.usecase.GetProductsCategoryUseCase
import com.algokelvin.movieapp.presentation.productcategory.ProductCategoryViewModelFactory
import dagger.Module
import dagger.Provides

@Module
class ProductCategoryModule {
    @ProductCategoryScope
    @Provides
    fun provideProductCategoryViewModelFactory(
        getProductsCategoryUseCase: GetProductsCategoryUseCase
    ): ProductCategoryViewModelFactory = ProductCategoryViewModelFactory(getProductsCategoryUseCase)
}