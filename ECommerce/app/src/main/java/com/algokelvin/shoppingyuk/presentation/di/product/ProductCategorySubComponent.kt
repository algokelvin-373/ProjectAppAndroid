package com.algokelvin.shoppingyuk.presentation.di.product

import com.algokelvin.shoppingyuk.presentation.productcategory.ProductCategoryFragment
import dagger.Subcomponent

@ProductCategoryScope
@Subcomponent(modules = [ProductCategoryModule::class])
interface ProductCategorySubComponent {
    fun inject(productCategoryFragment: ProductCategoryFragment)

    @Subcomponent.Factory
    interface Factory {
        fun create(): ProductCategorySubComponent
    }
}