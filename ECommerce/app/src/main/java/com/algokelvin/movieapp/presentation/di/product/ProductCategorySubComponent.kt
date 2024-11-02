package com.algokelvin.movieapp.presentation.di.product

import com.algokelvin.movieapp.presentation.productcategory.ProductCategoryActivity
import dagger.Subcomponent

@ProductCategoryScope
@Subcomponent(modules = [ProductCategoryModule::class])
interface ProductCategorySubComponent {
    fun inject(productCategoryActivity: ProductCategoryActivity)

    @Subcomponent.Factory
    interface Factory {
        fun create(): ProductCategorySubComponent
    }
}