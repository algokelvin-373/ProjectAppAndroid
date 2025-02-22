package com.algokelvin.movieapp.presentation.di.core

import com.algokelvin.movieapp.data.api.MovieApiService
import com.algokelvin.movieapp.data.repository.artist.datasource.ArtistRemoteDataSource
import com.algokelvin.movieapp.data.repository.artist.datasourceImpl.ArtistRemoteDataSourceImpl
import com.algokelvin.movieapp.data.repository.movie.datasource.MovieRemoteDataSource
import com.algokelvin.movieapp.data.repository.movie.datasourceImpl.MovieRemoteDataSourceImpl
import com.algokelvin.movieapp.data.repository.tv.datasource.TvShowRemoteDataSource
import com.algokelvin.movieapp.data.repository.tv.datasourceImpl.TvShowRemoteDataSourceImpl
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class RemoteDataModule(private val apiKey: String) {
    @Singleton
    @Provides
    fun provideMovieRemoteDataModule(movieApiService: MovieApiService): MovieRemoteDataSource {
        return MovieRemoteDataSourceImpl(movieApiService, apiKey)
    }

    @Singleton
    @Provides
    fun provideTvShowRemoteDataModule(movieApiService: MovieApiService): TvShowRemoteDataSource {
        return TvShowRemoteDataSourceImpl(movieApiService, apiKey)
    }

    @Singleton
    @Provides
    fun provideArtistRemoteDataModule(movieApiService: MovieApiService): ArtistRemoteDataSource {
        return ArtistRemoteDataSourceImpl(movieApiService, apiKey)
    }
}