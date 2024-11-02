package com.algokelvin.movieapp.presentation.di.core

import com.algokelvin.movieapp.data.repository.artist.ArtistRepositoryImpl
import com.algokelvin.movieapp.data.repository.artist.datasource.ArtistCacheDataSource
import com.algokelvin.movieapp.data.repository.artist.datasource.ArtistLocalDataSource
import com.algokelvin.movieapp.data.repository.artist.datasource.ArtistRemoteDataSource
import com.algokelvin.movieapp.data.repository.login.LoginRepositoryImpl
import com.algokelvin.movieapp.data.repository.login.datasource.LoginRemoteDataSource
import com.algokelvin.movieapp.data.repository.product.ProductRepositoryImpl
import com.algokelvin.movieapp.data.repository.product.datasource.ProductCacheDataSource
import com.algokelvin.movieapp.data.repository.product.datasource.ProductLocalDataSource
import com.algokelvin.movieapp.data.repository.product.datasource.ProductRemoteDataSource
import com.algokelvin.movieapp.data.repository.productCategory.ProductCategoryRepositoryImpl
import com.algokelvin.movieapp.data.repository.productCategory.datasource.ProductCategoryRemoteDataSource
import com.algokelvin.movieapp.data.repository.productDetail.ProductDetailRepositoryImpl
import com.algokelvin.movieapp.data.repository.productDetail.datasource.ProductDetailRemoteDataSource
import com.algokelvin.movieapp.data.repository.tv.TvShowRepositoryImpl
import com.algokelvin.movieapp.data.repository.tv.datasource.TvShowCacheDataSource
import com.algokelvin.movieapp.data.repository.tv.datasource.TvShowLocalDataSource
import com.algokelvin.movieapp.data.repository.tv.datasource.TvShowRemoteDataSource
import com.algokelvin.movieapp.domain.repository.ArtistRepository
import com.algokelvin.movieapp.domain.repository.LoginRepository
import com.algokelvin.movieapp.domain.repository.ProductCategoryRepository
import com.algokelvin.movieapp.domain.repository.ProductDetailRepository
import com.algokelvin.movieapp.domain.repository.ProductRepository
import com.algokelvin.movieapp.domain.repository.TvShowRepository
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class RepositoryModule {
    @Provides
    @Singleton
    fun provideMovieRepository(
        productRemoteDataSource: ProductRemoteDataSource,
        productLocalDataSource: ProductLocalDataSource,
        productCacheDataSource: ProductCacheDataSource,
    ): ProductRepository {
        return ProductRepositoryImpl(
            productRemoteDataSource,
            productLocalDataSource,
            productCacheDataSource
        )
    }

    @Provides
    @Singleton
    fun provideTvShowRepository(
        tvShowRemoteDataSource: TvShowRemoteDataSource,
        tvShowLocalDataSource: TvShowLocalDataSource,
        tvShowCacheDataSource: TvShowCacheDataSource,
    ): TvShowRepository {
        return TvShowRepositoryImpl(
            tvShowRemoteDataSource,
            tvShowLocalDataSource,
            tvShowCacheDataSource
        )
    }

    @Provides
    @Singleton
    fun provideArtistRepository(
        artistRemoteDataSource: ArtistRemoteDataSource,
        artistLocalDataSource: ArtistLocalDataSource,
        artistCacheDataSource: ArtistCacheDataSource,
    ): ArtistRepository {
        return ArtistRepositoryImpl(
            artistRemoteDataSource,
            artistLocalDataSource,
            artistCacheDataSource
        )
    }

    @Provides
    @Singleton
    fun provideProductDetailRepository(
        productDetailRemoteDataSource: ProductDetailRemoteDataSource
    ): ProductDetailRepository {
        return ProductDetailRepositoryImpl(
            productDetailRemoteDataSource
        )
    }

    @Provides
    @Singleton
    fun provideLoginRepository(
        loginRemoteDataSource: LoginRemoteDataSource
    ): LoginRepository {
        return LoginRepositoryImpl(
            loginRemoteDataSource
        )
    }

    @Provides
    @Singleton
    fun provideProductCategory(
        productCategoryRemoteDataSource: ProductCategoryRemoteDataSource
    ): ProductCategoryRepository {
        return ProductCategoryRepositoryImpl(
            productCategoryRemoteDataSource
        )
    }
}