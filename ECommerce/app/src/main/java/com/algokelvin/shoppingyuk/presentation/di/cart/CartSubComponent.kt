package com.algokelvin.shoppingyuk.presentation.di.cart

import com.algokelvin.shoppingyuk.presentation.cart.CartActivity
import dagger.Subcomponent

@CartScope
@Subcomponent(modules = [CartModule::class])
interface CartSubComponent {
    fun inject(cartActivity: CartActivity)

    @Subcomponent.Factory
    interface Factory {
        fun create(): CartSubComponent
    }
}