package com.algokelvin.movieapp.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.algokelvin.movieapp.data.model.cart.CartDB
import com.algokelvin.movieapp.data.model.product.Product
import com.algokelvin.movieapp.data.model.user.User

@Database(
    entities = [Product::class, User::class, CartDB::class],
    version = 4,
    exportSchema = false
)
abstract class ProductDB: RoomDatabase() {
    abstract fun productDao(): ProductDao
    abstract fun userDao(): UserDao
    abstract fun cartDao(): CartDao
}