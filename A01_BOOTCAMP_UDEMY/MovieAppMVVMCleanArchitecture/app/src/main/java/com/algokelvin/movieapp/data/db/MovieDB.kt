package com.algokelvin.movieapp.data.db

import androidx.room.Database
import com.algokelvin.movieapp.data.model.artist.Artist
import com.algokelvin.movieapp.data.model.movie.Movie
import com.algokelvin.movieapp.data.model.tv.TvShow

@Database(
    entities = [Movie::class, TvShow::class, Artist::class],
    version = 1,
    exportSchema = false
)
abstract class MovieDB {
    abstract fun movieDao(): MovieDao
    abstract fun tvShowDao(): TvShowDao
    abstract fun artistDao(): ArtistDao


}