package com.algokelvin.shoppingyuk.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.algokelvin.shoppingyuk.data.model.cart.CartDB
import com.algokelvin.shoppingyuk.data.model.product.Product
import com.algokelvin.shoppingyuk.data.model.user.User

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