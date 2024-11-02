package com.algokelvin.movieapp.presentation.di.product

import com.algokelvin.movieapp.presentation.product.ProductFragment
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