package algokelvin.app.movietvclient.data.db

import algokelvin.app.movietvclient.data.model.tvshows.TvShow
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface TvShowDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveTvShows(tvShows: List<TvShow>)

    @Query("SELECT * FROM popular_tvShows")
    suspend fun getTvShows(): List<TvShow>

    @Query("DELETE FROM popular_tvShows")
    suspend fun deleteTvShows()
}