package com.algokelvin.movieapp.presentation.di.product

import com.algokelvin.movieapp.presentation.product.ProductActivity
import dagger.Subcomponent

@ProductScope
@Subcomponent(modules = [ProductModule::class])
interface ProductSubComponent {
    fun inject(productActivity: ProductActivity)

    @Subcomponent.Factory
    interface Factory {
        fun create(): ProductSubComponent
    }
}