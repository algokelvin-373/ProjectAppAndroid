package com.algokelvin.shoppingyuk.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.algokelvin.shoppingyuk.data.model.cart.CartDB

@Dao
interface CartDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addProductInCart(cartDB: CartDB)

    @Query("SELECT * FROM cart_data WHERE cart_user_id = :userId")
    suspend fun getCart(userId: Int): List<CartDB>

    @Query("UPDATE cart_data SET cart_count = :count WHERE cart_user_id = :userId AND cart_product_id = :productId")
    suspend fun updateCountProductInCart(userId: Int, productId: Int, count: Int)

    @Query("DELETE FROM cart_data WHERE cart_user_id = :userId AND cart_product_id = :productId")
    suspend fun deleteProductInCart(userId: Int, productId: Int)

    @Query("DELETE FROM cart_data WHERE cart_user_id = :userId")
    suspend fun deleteProductForCheckout(userId: Int)
}