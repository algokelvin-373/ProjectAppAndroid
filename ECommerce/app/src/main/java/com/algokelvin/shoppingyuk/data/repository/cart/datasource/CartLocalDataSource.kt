package com.algokelvin.shoppingyuk.data.repository.cart.datasource

import com.algokelvin.shoppingyuk.data.model.cart.CartDB

interface CartLocalDataSource {
    suspend fun getCart(userId: Int): List<CartDB>
    suspend fun addProductInCart(cartDB: CartDB)
    suspend fun updateCountProductInCart(userId: Int, productId: Int, count: Int)
    suspend fun deleteProductInCart(userId: Int, productId: Int)
    suspend fun deleteAllForCheckout(userId: Int)
}