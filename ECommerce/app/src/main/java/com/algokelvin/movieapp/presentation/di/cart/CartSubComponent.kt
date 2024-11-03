package com.algokelvin.movieapp.presentation.di.cart

import com.algokelvin.movieapp.presentation.cart.CartActivity
import com.algokelvin.movieapp.presentation.login.LoginActivity
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