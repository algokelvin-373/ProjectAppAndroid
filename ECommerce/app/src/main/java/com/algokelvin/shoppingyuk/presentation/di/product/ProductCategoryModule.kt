package com.algokelvin.shoppingyuk.presentation.di.product

import com.algokelvin.shoppingyuk.domain.usecase.GetProductsCategoryUseCase
import com.algokelvin.shoppingyuk.presentation.productcategory.ProductCategoryViewModelFactory
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