package algokelvin.app.movietvclient.data.db

import algokelvin.app.movietvclient.data.model.artist.Artist
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface ArtistDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveArtists(tvShows: List<Artist>)

    @Query("SELECT * FROM popular_artists")
    suspend fun getArtists(): List<Artist>

    @Query("DELETE FROM popular_artists")
    suspend fun deleteArtists()
}