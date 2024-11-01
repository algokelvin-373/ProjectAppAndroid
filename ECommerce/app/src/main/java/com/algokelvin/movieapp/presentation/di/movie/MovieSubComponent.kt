package com.algokelvin.movieapp.presentation.di.movie

import com.algokelvin.movieapp.presentation.product.ProductActivity
import dagger.Subcomponent

@MovieScope
@Subcomponent(modules = [MovieModule::class])
interface MovieSubComponent {
    fun inject(productActivity: ProductActivity)

    @Subcomponent.Factory
    interface Factory {
        fun create(): MovieSubComponent
    }
}