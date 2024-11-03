package com.algokelvin.movieapp.domain.repository

import com.algokelvin.movieapp.data.api.ResponseResults
import com.algokelvin.movieapp.data.model.cart.Cart
import com.algokelvin.movieapp.data.model.cart.CartDB
import com.algokelvin.movieapp.data.model.cart.CartDetail

interface CartRepository {
    suspend fun addProductInCart(cartDB: CartDB): String
    suspend fun getCartByUserId(userId: Int): ResponseResults<List<CartDB>>
}