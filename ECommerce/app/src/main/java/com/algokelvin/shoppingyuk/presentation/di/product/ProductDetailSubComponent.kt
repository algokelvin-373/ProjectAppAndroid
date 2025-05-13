package com.algokelvin.shoppingyuk.presentation.di.product

import com.algokelvin.shoppingyuk.presentation.productdetail.ProductDetailActivity
import dagger.Subcomponent

@ProductDetailScope
@Subcomponent(modules = [ProductDetailModule::class])
interface ProductDetailSubComponent {
    fun inject(productDetailActivity: ProductDetailActivity)

    @Subcomponent.Factory
    interface Factory {
        fun create(): ProductDetailSubComponent
    }
}