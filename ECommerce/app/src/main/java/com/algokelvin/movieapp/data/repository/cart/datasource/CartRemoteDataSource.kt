package com.algokelvin.movieapp.data.repository.cart.datasource

import com.algokelvin.movieapp.data.model.cart.Cart
import com.algokelvin.movieapp.data.model.product.Product
import retrofit2.Response
import java.util.ArrayList

interface CartRemoteDataSource {
    suspend fun getCartByUserid(id: String): Response<ArrayList<Cart>>
}