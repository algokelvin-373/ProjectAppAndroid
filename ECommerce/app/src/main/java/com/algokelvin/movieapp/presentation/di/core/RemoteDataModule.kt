package com.algokelvin.movieapp.presentation.di.core

import com.algokelvin.movieapp.data.api.ProductApiService
import com.algokelvin.movieapp.data.repository.artist.datasource.ArtistRemoteDataSource
import com.algokelvin.movieapp.data.repository.artist.datasourceImpl.ArtistRemoteDataSourceImpl
import com.algokelvin.movieapp.data.repository.login.datasource.LoginRemoteDataSource
import com.algokelvin.movieapp.data.repository.login.datasourceImpl.LoginRemoteDataSourceImpl
import com.algokelvin.movieapp.data.repository.product.datasource.ProductRemoteDataSource
import com.algokelvin.movieapp.data.repository.product.datasourceImpl.ProductRemoteDataSourceImpl
import com.algokelvin.movieapp.data.repository.productCategory.datasource.ProductCategoryRemoteDataSource
import com.algokelvin.movieapp.data.repository.productCategory.datasourceImpl.ProductCategoryRemoteDataSourceImpl
import com.algokelvin.movieapp.data.repository.productDetail.datasource.ProductDetailRemoteDataSource
import com.algokelvin.movieapp.data.repository.productDetail.datasourceImpl.ProductDetailRemoteDataSourceImpl
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

    @Singleton
    @Provides
    fun provideProductDetailRemoteDataModule(productApiService: ProductApiService): ProductDetailRemoteDataSource {
        return ProductDetailRemoteDataSourceImpl(productApiService)
    }

    @Singleton
    @Provides
    fun provideLoginRemoteDataModule(productApiService: ProductApiService): LoginRemoteDataSource {
        return LoginRemoteDataSourceImpl(productApiService)
    }

    @Singleton
    @Provides
    fun provideProductCategoryRemoteDataModule(productApiService: ProductApiService): ProductCategoryRemoteDataSource {
        return ProductCategoryRemoteDataSourceImpl(productApiService)
    }
}