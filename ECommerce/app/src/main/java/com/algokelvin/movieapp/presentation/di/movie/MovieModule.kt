package com.algokelvin.movieapp.presentation.di.movie

import com.algokelvin.movieapp.domain.usecase.GetProductsUseCase
import com.algokelvin.movieapp.domain.usecase.UpdateMoviesUseCase
import com.algokelvin.movieapp.presentation.movie.ProductViewModelFactory
import dagger.Module
import dagger.Provides

@Module
class MovieModule {
    @MovieScope
    @Provides
    fun provideMovieViewModelFactory(
        getProductsUseCase: GetProductsUseCase,
        updateMovieUseCase: UpdateMoviesUseCase,
    ): ProductViewModelFactory = ProductViewModelFactory(getProductsUseCase, updateMovieUseCase)
}