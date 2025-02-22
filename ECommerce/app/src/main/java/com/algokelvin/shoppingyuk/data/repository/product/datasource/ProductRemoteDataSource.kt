package com.algokelvin.shoppingyuk.data.repository.product.datasource

import com.algokelvin.shoppingyuk.data.model.product.Product
import retrofit2.Response

interface ProductRemoteDataSource {
    suspend fun getProducts(): Response<ArrayList<Product>>
}