package com.algokelvin.movieapp.data.repository.cart.datasource

import com.algokelvin.movieapp.data.model.cart.CartDB

interface CartLocalDataSource {
    suspend fun getCart(userId: Int): List<CartDB>
    suspend fun addProductInCart(cartDB: CartDB)
    suspend fun updateCountProductInCart(productId: Int)
    suspend fun deleteProductInCart(productId: Int)
}