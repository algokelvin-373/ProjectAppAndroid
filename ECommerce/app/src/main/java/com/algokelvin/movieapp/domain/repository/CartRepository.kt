package com.algokelvin.movieapp.domain.repository

import com.algokelvin.movieapp.data.api.ResponseResults
import com.algokelvin.movieapp.data.model.cart.CartDB

interface CartRepository {
    suspend fun addProductInCart(cartDB: CartDB): String
    suspend fun getCartByUserId(userId: Int): ResponseResults<List<CartDB>>
    suspend fun updateCountProductInCart(userId: Int, productId: Int, count: Int): String
    suspend fun deleteProductInCart(userId: Int, productId: Int): String
    suspend fun deleteProductForCheckout(userId: Int): String
}