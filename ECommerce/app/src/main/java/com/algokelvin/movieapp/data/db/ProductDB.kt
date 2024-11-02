package com.algokelvin.movieapp.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.algokelvin.movieapp.data.model.product.Product

@Database(
    entities = [Product::class],
    version = 1,
    exportSchema = false
)
abstract class ProductDB(): RoomDatabase() {
    abstract fun productDao(): ProductDao
}