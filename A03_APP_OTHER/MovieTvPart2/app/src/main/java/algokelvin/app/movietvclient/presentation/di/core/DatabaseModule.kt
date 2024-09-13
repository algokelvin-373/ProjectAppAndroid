package algokelvin.app.movietvclient.presentation.di.core

import algokelvin.app.movietvclient.data.db.ArtistDao
import algokelvin.app.movietvclient.data.db.MovieDao
import algokelvin.app.movietvclient.data.db.MovieTvDatabase
import algokelvin.app.movietvclient.data.db.TvShowDao
import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DatabaseModule {

    @Singleton
    @Provides
    fun provideMovieTvDatabase(context: Context): MovieTvDatabase {
        return Room.databaseBuilder(
            context,
            MovieTvDatabase::class.java,
            "movie_tv_database"
        ).build()
    }

    @Singleton
    @Provides
    fun provideMovieDao(movieTvDatabase: MovieTvDatabase): MovieDao {
        return movieTvDatabase.movieDao()
    }

    @Singleton
    @Provides
    fun provideTvShowDao(movieTvDatabase: MovieTvDatabase): TvShowDao {
        return movieTvDatabase.tvShowDao()
    }

    @Singleton
    @Provides
    fun provideArtistDao(movieTvDatabase: MovieTvDatabase): ArtistDao {
        return movieTvDatabase.artisDao()
    }

}