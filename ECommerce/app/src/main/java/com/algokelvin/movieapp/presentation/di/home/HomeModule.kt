package com.algokelvin.movieapp.presentation.di.home

import com.algokelvin.movieapp.domain.usecase.GetProfileFromDBUseCase
import com.algokelvin.movieapp.presentation.home.HomeViewModelFactory
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