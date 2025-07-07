package com.algokelvin.movieapp.presentation.di.core

import com.algokelvin.movieapp.domain.repository.ArtistRepository
import com.algokelvin.movieapp.domain.repository.MovieRepository
import com.algokelvin.movieapp.domain.repository.TvShowRepository
import com.algokelvin.movieapp.domain.usecase.GetArtistsUseCase
import com.algokelvin.movieapp.domain.usecase.GetMoviesUseCase
import com.algokelvin.movieapp.domain.usecase.GetTvShowsUseCase
import com.algokelvin.movieapp.domain.usecase.UpdateArtistsUseCase
import com.algokelvin.movieapp.domain.usecase.UpdateMoviesUseCase
import com.algokelvin.movieapp.domain.usecase.UpdateTvShowsUseCase
import dagger.Module
import dagger.Provides

@Module
class UseCaseModule {
    @Provides
    fun provideGetMovieUseCase(movieRepository: MovieRepository): GetMoviesUseCase {
        return GetMoviesUseCase(movieRepository)
    }

    @Provides
    fun provideUpdateMovieUseCase(movieRepository: MovieRepository): UpdateMoviesUseCase {
        return UpdateMoviesUseCase(movieRepository)
    }

    @Provides
    fun provideGetTvShowUseCase(tvShowRepository: TvShowRepository): GetTvShowsUseCase {
        return GetTvShowsUseCase(tvShowRepository)
    }

    @Provides
    fun provideUpdateTvShowUseCase(tvShowRepository: TvShowRepository): UpdateTvShowsUseCase {
        return UpdateTvShowsUseCase(tvShowRepository)
    }

    @Provides
    fun provideGetArtistUseCase(artistRepository: ArtistRepository): GetArtistsUseCase {
        return GetArtistsUseCase(artistRepository)
    }

    @Provides
    fun provideUpdateArtistUseCase(artistRepository: ArtistRepository): UpdateArtistsUseCase {
        return UpdateArtistsUseCase(artistRepository)
    }
}