package algokelvin.app.movietvclient.presentation.di.artist

import algokelvin.app.movietvclient.domain.usecase.artist.GetArtistsUseCase
import algokelvin.app.movietvclient.domain.usecase.artist.UpdateArtistsUseCase
import algokelvin.app.movietvclient.presentation.artist.ArtistViewModelFactory
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
        return ArtistViewModelFactory(getArtistsUseCase, updateArtistsUseCase)
    }

}