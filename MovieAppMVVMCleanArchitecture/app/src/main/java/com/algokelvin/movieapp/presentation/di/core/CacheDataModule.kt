package com.algokelvin.movieapp.presentation.di.core

import com.algokelvin.movieapp.data.repository.artist.datasource.ArtistCacheDataSource
import com.algokelvin.movieapp.data.repository.artist.datasourceImpl.ArtistCacheDataSourceImpl
import com.algokelvin.movieapp.data.repository.movie.datasource.MovieCacheDataSource
import com.algokelvin.movieapp.data.repository.movie.datasourceImpl.MovieCacheDataSourceImpl
import com.algokelvin.movieapp.data.repository.tv.datasource.TvShowCacheDataSource
import com.algokelvin.movieapp.data.repository.tv.datasourceImpl.TvShowCacheDataSourceImpl
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class CacheDataModule {
    @Singleton
    @Provides
    fun provideMovieCacheDataModule(): MovieCacheDataSource {
        return MovieCacheDataSourceImpl()
    }

    @Singleton
    @Provides
    fun provideTvShowCacheDataModule(): TvShowCacheDataSource {
        return TvShowCacheDataSourceImpl()
    }

    @Singleton
    @Provides
    fun provideArtistCacheDataModule(): ArtistCacheDataSource {
        return ArtistCacheDataSourceImpl()
    }
}