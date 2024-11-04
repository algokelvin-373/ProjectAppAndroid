package com.algokelvin.movieapp.presentation.di.checkout

import com.algokelvin.movieapp.presentation.checkout.CheckoutActivity
import dagger.Subcomponent

@CheckoutScope
@Subcomponent(modules = [CheckoutModule::class])
interface CheckoutSubComponent {
    fun inject(checkoutActivity: CheckoutActivity)

    @Subcomponent.Factory
    interface Factory {
        fun create(): CheckoutSubComponent
    }
}