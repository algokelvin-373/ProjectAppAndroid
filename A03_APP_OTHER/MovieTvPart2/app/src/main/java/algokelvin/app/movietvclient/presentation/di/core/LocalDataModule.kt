package algokelvin.app.movietvclient.presentation.di.core

import algokelvin.app.movietvclient.data.db.ArtistDao
import algokelvin.app.movietvclient.data.db.MovieDao
import algokelvin.app.movietvclient.data.db.TvShowDao
import algokelvin.app.movietvclient.data.repository.artists.datasource.ArtistLocalDataSource
import algokelvin.app.movietvclient.data.repository.artists.datasourceImpl.ArtistLocalDataSourceImpl
import algokelvin.app.movietvclient.data.repository.movies.datasource.MovieLocalDataSource
import algokelvin.app.movietvclient.data.repository.movies.datasourceImpl.MovieLocalDataSourceImpl
import algokelvin.app.movietvclient.data.repository.tvshows.datasource.TvShowLocalDataSource
import algokelvin.app.movietvclient.data.repository.tvshows.datasourceImpl.TvShowLocalDataSourceImpl
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class LocalDataModule {

    @Singleton
    @Provides
    fun provideMovieLocalData(movieDao: MovieDao): MovieLocalDataSource {
        return MovieLocalDataSourceImpl(movieDao)
    }

    @Singleton
    @Provides
    fun provideTvShowLocalData(tvShowDao: TvShowDao): TvShowLocalDataSource {
        return TvShowLocalDataSourceImpl(tvShowDao)
    }

    @Singleton
    @Provides
    fun provideArtistLocalData(artistDao: ArtistDao): ArtistLocalDataSource {
        return ArtistLocalDataSourceImpl(artistDao)
    }

}