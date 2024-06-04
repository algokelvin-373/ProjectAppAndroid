package com.anushka.tmdbclient.presentation.di.core

import com.anushka.tmdbclient.data.repository.artist.ArtistRepositoryImpl
import com.anushka.tmdbclient.data.repository.artist.datasource.ArtistCacheDataSource
import com.anushka.tmdbclient.data.repository.artist.datasource.ArtistLocalDataSource
import com.anushka.tmdbclient.data.repository.artist.datasource.ArtistRemoteDatasource
import com.anushka.tmdbclient.data.repository.movie.MovieRepositoryImpl
import com.anushka.tmdbclient.data.repository.movie.datasource.MovieCacheDataSource
import com.anushka.tmdbclient.data.repository.movie.datasource.MovieLocalDataSource
import com.anushka.tmdbclient.data.repository.movie.datasource.MovieRemoteDatasource
import com.anushka.tmdbclient.data.repository.tvshow.TvShowRepositoryImpl
import com.anushka.tmdbclient.data.repository.tvshow.datasource.TvShowCacheDataSource
import com.anushka.tmdbclient.data.repository.tvshow.datasource.TvShowLocalDataSource
import com.anushka.tmdbclient.data.repository.tvshow.datasource.TvShowRemoteDatasource
import com.anushka.tmdbclient.domain.repository.ArtistRepository
import com.anushka.tmdbclient.domain.repository.MovieRepository
import com.anushka.tmdbclient.domain.repository.TvShowRepository
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class RepositoryModule {

    @Provides
    @Singleton
    fun provideMovieRepository(
        movieRemoteDatasource: MovieRemoteDatasource,
        movieLocalDataSource: MovieLocalDataSource,
        movieCacheDataSource: MovieCacheDataSource
    ): MovieRepository {

        return MovieRepositoryImpl(
            movieRemoteDatasource,
            movieLocalDataSource,
            movieCacheDataSource
        )


    }

    @Provides
    @Singleton
    fun provideTvShowRepository(
        tvShowRemoteDatasource: TvShowRemoteDatasource,
        tvShowLocalDataSource: TvShowLocalDataSource,
        tvShowCacheDataSource: TvShowCacheDataSource
    ): TvShowRepository {

        return TvShowRepositoryImpl(
            tvShowRemoteDatasource,
            tvShowLocalDataSource,
            tvShowCacheDataSource
        )


    }

    @Provides
    @Singleton
    fun provideArtistRepository(
        artistRemoteDatasource: ArtistRemoteDatasource,
        artistLocalDataSource: ArtistLocalDataSource,
        artistCacheDataSource: ArtistCacheDataSource
    ): ArtistRepository {

        return ArtistRepositoryImpl(
            artistRemoteDatasource,
            artistLocalDataSource,
            artistCacheDataSource
        )


    }

}