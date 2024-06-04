package algokelvin.app.movietvclient.data.db

import algokelvin.app.movietvclient.data.model.artist.Artist
import algokelvin.app.movietvclient.data.model.movies.Movie
import algokelvin.app.movietvclient.data.model.tvshows.TvShow
import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    entities = [Movie::class, TvShow::class, Artist::class],
    version = 1,
    exportSchema = false
)
abstract class MovieTvDatabase: RoomDatabase() {
    abstract fun movieDao(): MovieDao
    abstract fun tvShowDao(): TvShowDao
    abstract fun artisDao(): ArtistDao

    companion object{
        @Volatile
        private var INSTANCE : MovieTvDatabase? = null
        fun getInstance(context: Context): MovieTvDatabase {
            synchronized(this){
                var instance = INSTANCE
                if(instance==null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        MovieTvDatabase::class.java,
                        "movie_tv_database"
                    ).build()
                    INSTANCE = instance
                }
                return instance
            }
        }
    }
}