package algokelvin.app.movietvclient.presentation.di.core

import algokelvin.app.movietvclient.data.api.MovieTvServices
import algokelvin.app.movietvclient.data.repository.artists.datasource.ArtistRemoteDataSource
import algokelvin.app.movietvclient.data.repository.artists.datasourceImpl.ArtistRemoteDataSourceImpl
import algokelvin.app.movietvclient.data.repository.movies.datasource.MovieRemoteDataSource
import algokelvin.app.movietvclient.data.repository.movies.datasourceImpl.MovieRemoteDataSourceImpl
import algokelvin.app.movietvclient.data.repository.tvshows.datasource.TvShowRemoteDataSource
import algokelvin.app.movietvclient.data.repository.tvshows.datasourceImpl.TvShowRemoteDataSourceImpl
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class RemoteDataModule(private val apiKey: String) {

    @Singleton
    @Provides
    fun provideMovieRemoteData(movieTvServices: MovieTvServices): MovieRemoteDataSource {
        return MovieRemoteDataSourceImpl(movieTvServices, apiKey)
    }

    @Singleton
    @Provides
    fun provideTvShowRemoteData(movieTvServices: MovieTvServices): TvShowRemoteDataSource {
        return TvShowRemoteDataSourceImpl(movieTvServices, apiKey)
    }

    @Singleton
    @Provides
    fun provideArtistRemoteData(movieTvServices: MovieTvServices): ArtistRemoteDataSource {
        return ArtistRemoteDataSourceImpl(movieTvServices, apiKey)
    }

}