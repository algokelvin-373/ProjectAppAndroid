package algokelvin.app.movietvclient.presentation.di.core

import algokelvin.app.movietvclient.data.repository.artists.datasource.ArtistCacheDataSource
import algokelvin.app.movietvclient.data.repository.artists.datasource.ArtistLocalDataSource
import algokelvin.app.movietvclient.data.repository.artists.datasource.ArtistRemoteDataSource
import algokelvin.app.movietvclient.data.repository.artists.datasourceImpl.ArtistRepositoryImpl
import algokelvin.app.movietvclient.data.repository.movies.datasource.MovieCacheDataSource
import algokelvin.app.movietvclient.data.repository.movies.datasource.MovieLocalDataSource
import algokelvin.app.movietvclient.data.repository.movies.datasource.MovieRemoteDataSource
import algokelvin.app.movietvclient.data.repository.movies.datasourceImpl.MovieRepositoryImpl
import algokelvin.app.movietvclient.data.repository.tvshows.datasource.TvShowCacheDataSource
import algokelvin.app.movietvclient.data.repository.tvshows.datasource.TvShowLocalDataSource
import algokelvin.app.movietvclient.data.repository.tvshows.datasource.TvShowRemoteDataSource
import algokelvin.app.movietvclient.data.repository.tvshows.datasourceImpl.TvShowRepositoryImpl
import algokelvin.app.movietvclient.domain.repository.ArtistsRepository
import algokelvin.app.movietvclient.domain.repository.MovieRepository
import algokelvin.app.movietvclient.domain.repository.TvShowRepository
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class RepositoryModule {

    @Singleton
    @Provides
    fun provideMovieRepository(
        movieRemoteDataSource: MovieRemoteDataSource,
        movieLocalDataSource: MovieLocalDataSource,
        movieCacheDataSource: MovieCacheDataSource
    ): MovieRepository {
        return MovieRepositoryImpl(
            movieRemoteDataSource,
            movieLocalDataSource,
            movieCacheDataSource
        )
    }

    @Singleton
    @Provides
    fun provideTvShowRepository(
        tvShowRemoteDataSource: TvShowRemoteDataSource,
        tvShowLocalDataSource: TvShowLocalDataSource,
        tvShowCacheDataSource: TvShowCacheDataSource
    ): TvShowRepository {
        return TvShowRepositoryImpl(
            tvShowRemoteDataSource,
            tvShowLocalDataSource,
            tvShowCacheDataSource
        )
    }

    @Singleton
    @Provides
    fun provideArtistRepository(
        artistRemoteDataSource: ArtistRemoteDataSource,
        artistLocalDataSource: ArtistLocalDataSource,
        artistCacheDataSource: ArtistCacheDataSource
    ): ArtistsRepository {
        return ArtistRepositoryImpl(
            artistRemoteDataSource,
            artistLocalDataSource,
            artistCacheDataSource
        )
    }

}