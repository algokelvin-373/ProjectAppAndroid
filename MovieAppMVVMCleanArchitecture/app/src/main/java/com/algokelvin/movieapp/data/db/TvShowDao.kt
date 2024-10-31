package com.algokelvin.movieapp.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.algokelvin.movieapp.data.model.tv.TvShow

@Dao
interface TvShowDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveTvShows(tvShows: List<TvShow>)

    @Query("DELETE FROM popular_tvShows")
    suspend fun deleteAllTvShows()

    @Query("SELECT * FROM popular_tvShows")
    suspend fun getAllTvShows(): List<TvShow>
}