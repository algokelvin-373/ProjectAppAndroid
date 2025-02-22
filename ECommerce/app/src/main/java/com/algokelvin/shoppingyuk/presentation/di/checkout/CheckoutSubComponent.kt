package com.algokelvin.shoppingyuk.presentation.di.checkout

import com.algokelvin.shoppingyuk.presentation.checkout.CheckoutActivity
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