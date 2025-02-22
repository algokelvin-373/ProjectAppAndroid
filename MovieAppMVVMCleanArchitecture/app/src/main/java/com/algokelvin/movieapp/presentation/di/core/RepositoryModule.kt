package com.algokelvin.movieapp.presentation.di.core

import com.algokelvin.movieapp.data.repository.artist.ArtistRepositoryImpl
import com.algokelvin.movieapp.data.repository.artist.datasource.ArtistCacheDataSource
import com.algokelvin.movieapp.data.repository.artist.datasource.ArtistLocalDataSource
import com.algokelvin.movieapp.data.repository.artist.datasource.ArtistRemoteDataSource
import com.algokelvin.movieapp.data.repository.movie.MovieRepositoryImpl
import com.algokelvin.movieapp.data.repository.movie.datasource.MovieCacheDataSource
import com.algokelvin.movieapp.data.repository.movie.datasource.MovieLocalDataSource
import com.algokelvin.movieapp.data.repository.movie.datasource.MovieRemoteDataSource
import com.algokelvin.movieapp.data.repository.tv.TvShowRepositoryImpl
import com.algokelvin.movieapp.data.repository.tv.datasource.TvShowCacheDataSource
import com.algokelvin.movieapp.data.repository.tv.datasource.TvShowLocalDataSource
import com.algokelvin.movieapp.data.repository.tv.datasource.TvShowRemoteDataSource
import com.algokelvin.movieapp.domain.repository.ArtistRepository
import com.algokelvin.movieapp.domain.repository.MovieRepository
import com.algokelvin.movieapp.domain.repository.TvShowRepository
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class RepositoryModule {
    @Provides
    @Singleton
    fun provideMovieRepository(
        movieRemoteDataSource: MovieRemoteDataSource,
        movieLocalDataSource: MovieLocalDataSource,
        movieCacheDataSource: MovieCacheDataSource,
    ): MovieRepository {
        return MovieRepositoryImpl(
            movieRemoteDataSource,
            movieLocalDataSource,
            movieCacheDataSource
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
}