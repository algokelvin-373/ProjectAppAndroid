package com.algokelvin.movieapp.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.algokelvin.movieapp.data.model.artist.Artist
import com.algokelvin.movieapp.data.model.Product
import com.algokelvin.movieapp.data.model.tv.TvShow

@Database(
    entities = [Product::class, TvShow::class, Artist::class],
    version = 1,
    exportSchema = false
)
abstract class ProductDB(): RoomDatabase() {
    abstract fun productDao(): ProductDao
    abstract fun tvShowDao(): TvShowDao
    abstract fun artistDao(): ArtistDao


}