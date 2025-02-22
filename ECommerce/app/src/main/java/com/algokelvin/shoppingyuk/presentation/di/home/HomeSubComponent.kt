package com.algokelvin.shoppingyuk.presentation.di.home

import com.algokelvin.shoppingyuk.presentation.home.HomeActivity
import dagger.Subcomponent

@HomeScope
@Subcomponent(modules = [HomeModule::class])
interface HomeSubComponent {
    fun inject(homeActivity: HomeActivity)

    @Subcomponent.Factory
    interface Factory {
        fun create(): HomeSubComponent
    }
}