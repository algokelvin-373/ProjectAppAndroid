package com.algokelvin.movieapp.data.repository.product.datasource

import com.algokelvin.movieapp.data.model.Product
import retrofit2.Response

interface ProductRemoteDataSource {
    suspend fun getProducts(): Response<ArrayList<Product>>
}