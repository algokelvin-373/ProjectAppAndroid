package com.algokelvin.movieapp.domain.repository

import com.algokelvin.movieapp.data.api.ResponseResults
import com.algokelvin.movieapp.data.model.cart.Cart
import com.algokelvin.movieapp.data.model.cart.CartDetail

interface CartRepository {
    suspend fun getCartByUserId(id: String): ResponseResults<CartDetail>
}