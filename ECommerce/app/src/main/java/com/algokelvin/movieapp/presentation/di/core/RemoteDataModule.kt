package com.algokelvin.movieapp.presentation.di.core

import com.algokelvin.movieapp.data.api.ProductApiService
import com.algokelvin.movieapp.data.repository.artist.datasource.ArtistRemoteDataSource
import com.algokelvin.movieapp.data.repository.artist.datasourceImpl.ArtistRemoteDataSourceImpl
import com.algokelvin.movieapp.data.repository.movie.datasource.ProductRemoteDataSource
import com.algokelvin.movieapp.data.repository.movie.datasourceImpl.ProductRemoteDataSourceImpl
import com.algokelvin.movieapp.data.repository.tv.datasource.TvShowRemoteDataSource
import com.algokelvin.movieapp.data.repository.tv.datasourceImpl.TvShowRemoteDataSourceImpl
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class RemoteDataModule(private val apiKey: String) {
    @Singleton
    @Provides
    fun provideMovieRemoteDataModule(productApiService: ProductApiService): ProductRemoteDataSource {
        return ProductRemoteDataSourceImpl(productApiService, apiKey)
    }

    @Singleton
    @Provides
    fun provideTvShowRemoteDataModule(productApiService: ProductApiService): TvShowRemoteDataSource {
        return TvShowRemoteDataSourceImpl(productApiService, apiKey)
    }

    @Singleton
    @Provides
    fun provideArtistRemoteDataModule(productApiService: ProductApiService): ArtistRemoteDataSource {
        return ArtistRemoteDataSourceImpl(productApiService, apiKey)
    }
}