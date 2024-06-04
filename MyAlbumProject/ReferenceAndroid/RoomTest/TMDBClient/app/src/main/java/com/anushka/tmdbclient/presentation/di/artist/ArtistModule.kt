package com.anushka.tmdbclient.presentation.di.artist

import com.anushka.tmdbclient.domain.usecase.GetArtistsUseCase
import com.anushka.tmdbclient.domain.usecase.UpdateArtistsUseCase
import com.anushka.tmdbclient.presentation.artist.ArtistViewModelFactory
import dagger.Module
import dagger.Provides

@Module
class ArtistModule {
    @ArtistScope
    @Provides
    fun provideArtistViewModelFactory(
        getArtistsUseCase: GetArtistsUseCase,
        updateArtistsUseCase: UpdateArtistsUseCase
    ): ArtistViewModelFactory {
        return ArtistViewModelFactory(
            getArtistsUseCase,
            updateArtistsUseCase
        )
    }

}