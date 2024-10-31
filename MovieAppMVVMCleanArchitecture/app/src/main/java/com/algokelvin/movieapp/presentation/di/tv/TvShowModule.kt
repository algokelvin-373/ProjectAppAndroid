package com.algokelvin.movieapp.presentation.di.tv

import com.algokelvin.movieapp.domain.usecase.GetTvShowsUseCase
import com.algokelvin.movieapp.domain.usecase.UpdateTvShowsUseCase
import com.algokelvin.movieapp.presentation.tv.TvShowViewModelFactory
import dagger.Module
import dagger.Provides

@Module
class TvShowModule {
    @TvShowScope
    @Provides
    fun provideTvShowViewModelFactory(
        getTvShowsUseCase: GetTvShowsUseCase,
        updateTvShowsUseCase: UpdateTvShowsUseCase,
    ): TvShowViewModelFactory = TvShowViewModelFactory(getTvShowsUseCase, updateTvShowsUseCase)
}