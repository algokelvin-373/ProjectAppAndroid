package algokelvin.app.movietvclient.presentation.di.core

import algokelvin.app.movietvclient.domain.repository.ArtistsRepository
import algokelvin.app.movietvclient.domain.repository.MovieRepository
import algokelvin.app.movietvclient.domain.repository.TvShowRepository
import algokelvin.app.movietvclient.domain.usecase.artist.GetArtistsUseCase
import algokelvin.app.movietvclient.domain.usecase.artist.UpdateArtistsUseCase
import algokelvin.app.movietvclient.domain.usecase.movie.GetMoviesUseCase
import algokelvin.app.movietvclient.domain.usecase.movie.UpdateMoviesUseCase
import algokelvin.app.movietvclient.domain.usecase.tvShow.GetTvShowsUseCase
import algokelvin.app.movietvclient.domain.usecase.tvShow.UpdateTvShowsUseCase
import dagger.Module
import dagger.Provides

@Module
class UseCaseModule {

    @Provides
    fun provideGetMoviesUseCase(movieRepository: MovieRepository): GetMoviesUseCase {
        return GetMoviesUseCase(movieRepository)
    }

    @Provides
    fun provideUpdateMoviesUseCase(movieRepository: MovieRepository): UpdateMoviesUseCase {
        return UpdateMoviesUseCase(movieRepository)
    }

    @Provides
    fun provideGetTvShowsUseCase(tvShowRepository: TvShowRepository): GetTvShowsUseCase {
        return GetTvShowsUseCase(tvShowRepository)
    }

    @Provides
    fun provideUpdateTvShowsUseCase(tvShowRepository: TvShowRepository): UpdateTvShowsUseCase {
        return UpdateTvShowsUseCase(tvShowRepository)
    }

    @Provides
    fun provideGetArtistsUseCase(artistsRepository: ArtistsRepository): GetArtistsUseCase {
        return GetArtistsUseCase(artistsRepository)
    }

    @Provides
    fun provideUpdateArtistsUseCase(artistsRepository: ArtistsRepository): UpdateArtistsUseCase {
        return UpdateArtistsUseCase(artistsRepository)
    }

}