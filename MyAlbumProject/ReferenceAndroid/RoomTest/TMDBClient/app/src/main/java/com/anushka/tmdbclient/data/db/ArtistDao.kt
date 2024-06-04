package com.anushka.tmdbclient.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.anushka.tmdbclient.data.model.artist.Artist
import com.anushka.tmdbclient.data.model.tvshow.TvShow

@Dao
interface ArtistDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveArtists(artists : List<Artist>)

    @Query("DELETE FROM popular_artists")
    suspend fun deleteAllArtists()

    @Query("SELECT * FROM popular_artists")
    suspend fun getArtists():List<Artist>
}