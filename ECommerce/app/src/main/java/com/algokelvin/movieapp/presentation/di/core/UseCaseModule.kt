package com.algokelvin.movieapp.presentation.di.core

import com.algokelvin.movieapp.domain.repository.ArtistRepository
import com.algokelvin.movieapp.domain.repository.ProductDetailRepository
import com.algokelvin.movieapp.domain.repository.ProductRepository
import com.algokelvin.movieapp.domain.repository.TvShowRepository
import com.algokelvin.movieapp.domain.usecase.GetArtistsUseCase
import com.algokelvin.movieapp.domain.usecase.GetProductDetailUseCase
import com.algokelvin.movieapp.domain.usecase.GetProductsUseCase
import com.algokelvin.movieapp.domain.usecase.GetTvShowsUseCase
import com.algokelvin.movieapp.domain.usecase.UpdateArtistsUseCase
import com.algokelvin.movieapp.domain.usecase.UpdateMoviesUseCase
import com.algokelvin.movieapp.domain.usecase.UpdateTvShowsUseCase
import dagger.Module
import dagger.Provides

@Module
class UseCaseModule {
    @Provides
    fun provideGetMovieUseCase(productRepository: ProductRepository): GetProductsUseCase {
        return GetProductsUseCase(productRepository)
    }

    @Provides
    fun provideUpdateMovieUseCase(productRepository: ProductRepository): UpdateMoviesUseCase {
        return UpdateMoviesUseCase(productRepository)
    }

    @Provides
    fun provideGetTvShowUseCase(tvShowRepository: TvShowRepository): GetTvShowsUseCase {
        return GetTvShowsUseCase(tvShowRepository)
    }

    @Provides
    fun provideUpdateTvShowUseCase(tvShowRepository: TvShowRepository): UpdateTvShowsUseCase {
        return UpdateTvShowsUseCase(tvShowRepository)
    }

    @Provides
    fun provideGetArtistUseCase(artistRepository: ArtistRepository): GetArtistsUseCase {
        return GetArtistsUseCase(artistRepository)
    }

    @Provides
    fun provideUpdateArtistUseCase(artistRepository: ArtistRepository): UpdateArtistsUseCase {
        return UpdateArtistsUseCase(artistRepository)
    }

    @Provides
    fun provideGetProductDetailUseCase(productDetailRepository: ProductDetailRepository): GetProductDetailUseCase {
        return GetProductDetailUseCase(productDetailRepository)
    }
}