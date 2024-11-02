package com.algokelvin.movieapp.presentation.di.core

import com.algokelvin.movieapp.data.repository.artist.datasource.ArtistCacheDataSource
import com.algokelvin.movieapp.data.repository.artist.datasourceImpl.ArtistCacheDataSourceImpl
import com.algokelvin.movieapp.data.repository.product.datasource.ProductCacheDataSource
import com.algokelvin.movieapp.data.repository.product.datasourceImpl.ProductCacheDataSourceImpl
import com.algokelvin.movieapp.data.repository.tv.datasource.TvShowCacheDataSource
import com.algokelvin.movieapp.data.repository.tv.datasourceImpl.TvShowCacheDataSourceImpl
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class CacheDataModule {
    @Singleton
    @Provides
    fun provideMovieCacheDataModule(): ProductCacheDataSource {
        return ProductCacheDataSourceImpl()
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