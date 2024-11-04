package com.algokelvin.movieapp.data.repository.cart.datasourceImpl

import com.algokelvin.movieapp.data.api.ProductApiService
import com.algokelvin.movieapp.data.model.cart.Cart
import com.algokelvin.movieapp.data.repository.cart.datasource.CartRemoteDataSource
import retrofit2.Response

class CartRemoteDataSourceImpl(
    private val service: ProductApiService,
): CartRemoteDataSource {
    override suspend fun getCartByUserid(id: String): Response<ArrayList<Cart>> = service.getCartByIdUser(id)
}