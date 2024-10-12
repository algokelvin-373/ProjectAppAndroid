package algokelvin.app.movietvclient.presentation.di.core

import algokelvin.app.movietvclient.data.repository.artists.datasource.ArtistCacheDataSource
import algokelvin.app.movietvclient.data.repository.artists.datasourceImpl.ArtistCacheDataSourceImpl
import algokelvin.app.movietvclient.data.repository.movies.datasource.MovieCacheDataSource
import algokelvin.app.movietvclient.data.repository.movies.datasourceImpl.MovieCacheDataSourceImpl
import algokelvin.app.movietvclient.data.repository.tvshows.datasource.TvShowCacheDataSource
import algokelvin.app.movietvclient.data.repository.tvshows.datasourceImpl.TvShowCacheDataSourceImpl
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class CacheDataModule {

    @Singleton
    @Provides
    fun provideMovieCacheData(): MovieCacheDataSource {
        return MovieCacheDataSourceImpl()
    }

    @Singleton
    @Provides
    fun provideTvShowCacheData(): TvShowCacheDataSource {
        return TvShowCacheDataSourceImpl()
    }

    @Singleton
    @Provides
    fun provideArtistCacheData(): ArtistCacheDataSource {
        return ArtistCacheDataSourceImpl()
    }

}