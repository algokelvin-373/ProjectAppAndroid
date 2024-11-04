package com.algokelvin.movieapp.data.repository.cart.datasource

import com.algokelvin.movieapp.data.model.cart.Cart
import retrofit2.Response

interface CartRemoteDataSource {
    suspend fun getCartByUserid(id: String): Response<ArrayList<Cart>>
}