package com.algokelvin.movieapp.data.repository.cart.datasourceImpl

import com.algokelvin.movieapp.data.api.ProductApiService
import com.algokelvin.movieapp.data.model.cart.Cart
import com.algokelvin.movieapp.data.model.product.Product
import com.algokelvin.movieapp.data.repository.cart.datasource.CartRemoteDataSource
import com.algokelvin.movieapp.data.repository.product.datasource.ProductRemoteDataSource
import retrofit2.Response

class CartRemoteDataSourceImpl(
    private val service: ProductApiService,
): CartRemoteDataSource {
    override suspend fun getCartByUserid(id: String): Response<ArrayList<Cart>> = service.getCartByIdUser(id)
}