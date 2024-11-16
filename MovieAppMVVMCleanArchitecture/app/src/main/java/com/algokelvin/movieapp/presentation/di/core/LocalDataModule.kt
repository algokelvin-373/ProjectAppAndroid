package com.algokelvin.movieapp.presentation.di.core

import com.algokelvin.movieapp.data.db.ArtistDao
import com.algokelvin.movieapp.data.db.MovieDao
import com.algokelvin.movieapp.data.db.TvShowDao
import com.algokelvin.movieapp.data.repository.artist.datasource.ArtistLocalDataSource
import com.algokelvin.movieapp.data.repository.artist.datasourceImpl.ArtistLocalDataSourceImpl
import com.algokelvin.movieapp.data.repository.movie.datasource.MovieLocalDataSource
import com.algokelvin.movieapp.data.repository.movie.datasourceImpl.MovieLocalDataSourceImpl
import com.algokelvin.movieapp.data.repository.tv.datasource.TvShowLocalDataSource
import com.algokelvin.movieapp.data.repository.tv.datasourceImpl.TvShowLocalDataSourceImpl
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class LocalDataModule {
    @Singleton
    @Provides
    fun provideMovieLocalDataModule(movieDao: MovieDao): MovieLocalDataSource {
        return MovieLocalDataSourceImpl(movieDao)
    }

    @Singleton
    @Provides
    fun provideTvShowLocalDataModule(tvShowDao: TvShowDao): TvShowLocalDataSource {
        return TvShowLocalDataSourceImpl(tvShowDao)
    }

    @Singleton
    @Provides
    fun provideArtistLocalDataModule(artistDao: ArtistDao): ArtistLocalDataSource {
        return ArtistLocalDataSourceImpl(artistDao)
    }
}