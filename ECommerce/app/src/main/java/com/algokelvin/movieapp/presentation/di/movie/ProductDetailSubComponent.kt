package com.algokelvin.movieapp.presentation.di.movie

import com.algokelvin.movieapp.presentation.productdetail.ProductDetailActivity
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