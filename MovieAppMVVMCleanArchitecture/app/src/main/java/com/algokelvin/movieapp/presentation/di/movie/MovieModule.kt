package com.algokelvin.movieapp.presentation.di.movie

import com.algokelvin.movieapp.domain.usecase.GetMoviesUseCase
import com.algokelvin.movieapp.domain.usecase.UpdateMoviesUseCase
import com.algokelvin.movieapp.presentation.movie.MovieViewModelFactory
import dagger.Module
import dagger.Provides

@Module
class MovieModule {
    @MovieScope
    @Provides
    fun provideMovieViewModelFactory(
        getMoviesUseCase: GetMoviesUseCase,
        updateMovieUseCase: UpdateMoviesUseCase,
    ): MovieViewModelFactory = MovieViewModelFactory(getMoviesUseCase, updateMovieUseCase)
}