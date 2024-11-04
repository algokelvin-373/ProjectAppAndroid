package com.algokelvin.shoppingyuk.presentation.di.product

import com.algokelvin.shoppingyuk.presentation.product.ProductFragment
import dagger.Subcomponent

@ProductScope
@Subcomponent(modules = [ProductModule::class])
interface ProductSubComponent {
    fun inject(productFragment: ProductFragment)

    @Subcomponent.Factory
    interface Factory {
        fun create(): ProductSubComponent
    }
}