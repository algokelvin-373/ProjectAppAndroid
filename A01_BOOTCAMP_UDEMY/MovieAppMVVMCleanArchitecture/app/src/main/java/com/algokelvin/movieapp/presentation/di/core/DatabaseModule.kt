package com.algokelvin.movieapp.presentation.di.core

import android.content.Context
import androidx.room.Room
import com.algokelvin.movieapp.data.db.ArtistDao
import com.algokelvin.movieapp.data.db.MovieDB
import com.algokelvin.movieapp.data.db.MovieDao
import com.algokelvin.movieapp.data.db.TvShowDao
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DatabaseModule {
    @Singleton
    @Provides
    fun provideMovieDatabase(context: Context): MovieDB {
        return Room.databaseBuilder(context, MovieDB::class.java, "tmdbmovie")
            .build()
    }

    @Singleton
    @Provides
    fun provideMovieDao(movieDB: MovieDB): MovieDao {
        return movieDB.movieDao()
    }

    @Singleton
    @Provides
    fun provideTvShowDao(movieDB: MovieDB): TvShowDao {
        return movieDB.tvShowDao()
    }

    @Singleton
    @Provides
    fun provideArtistDao(movieDB: MovieDB): ArtistDao {
        return movieDB.artistDao()
    }
}