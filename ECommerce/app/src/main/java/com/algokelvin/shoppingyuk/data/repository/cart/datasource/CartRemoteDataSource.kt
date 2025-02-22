package com.algokelvin.shoppingyuk.data.repository.cart.datasource

import com.algokelvin.shoppingyuk.data.model.cart.Cart
import retrofit2.Response

interface CartRemoteDataSource {
    suspend fun getCartByUserid(id: String): Response<ArrayList<Cart>>
}