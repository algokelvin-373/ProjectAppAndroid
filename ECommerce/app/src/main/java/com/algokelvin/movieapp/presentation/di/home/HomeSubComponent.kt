package com.algokelvin.movieapp.presentation.di.home

import com.algokelvin.movieapp.presentation.home.HomeActivity
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