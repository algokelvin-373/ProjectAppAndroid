package com.algokelvin.shoppingyuk.presentation.di.home

import com.algokelvin.shoppingyuk.domain.usecase.GetProfileFromDBUseCase
import com.algokelvin.shoppingyuk.presentation.home.HomeViewModelFactory
import dagger.Module
import dagger.Provides

@Module
class HomeModule {
    @HomeScope
    @Provides
    fun provideHomeViewModelFactory(
        getProfileFromDBUseCase: GetProfileFromDBUseCase,
    ): HomeViewModelFactory = HomeViewModelFactory(getProfileFromDBUseCase)
}