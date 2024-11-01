package com.algokelvin.movieapp.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.algokelvin.movieapp.data.model.Product

@Dao
interface ProductDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveProducts(products: List<Product>)

    @Query("DELETE FROM products_data")
    suspend fun deleteAllProducts()

    @Query("SELECT * FROM products_data")
    suspend fun getAllProducts(): List<Product>
}