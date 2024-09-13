package algokelvin.app.movietvclient.data.db

import algokelvin.app.movietvclient.data.model.movies.Movie
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface MovieDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveMovies(movies: List<Movie>)

    @Query("SELECT * FROM popular_movies")
    suspend fun getMovies(): List<Movie>

    @Query("DELETE FROM popular_movies")
    suspend fun deleteAllMovies()
}