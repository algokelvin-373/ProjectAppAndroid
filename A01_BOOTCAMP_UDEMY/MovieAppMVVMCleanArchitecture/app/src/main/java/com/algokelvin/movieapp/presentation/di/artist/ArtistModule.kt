package com.algokelvin.movieapp.presentation.di.artist

import com.algokelvin.movieapp.domain.usecase.GetArtistsUseCase
import com.algokelvin.movieapp.domain.usecase.UpdateArtistsUseCase
import com.algokelvin.movieapp.presentation.artist.ArtistViewModelFactory
import dagger.Module
import dagger.Provides

@Module
class ArtistModule {
    @ArtistScope
    @Provides
    fun provideArtistViewModelFactory(
        getArtistsUseCase: GetArtistsUseCase,
        updateArtistsUseCase: UpdateArtistsUseCase,
    ): ArtistViewModelFactory = ArtistViewModelFactory(getArtistsUseCase, updateArtistsUseCase)
}