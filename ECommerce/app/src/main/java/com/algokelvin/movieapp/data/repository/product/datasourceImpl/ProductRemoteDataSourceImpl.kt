package com.algokelvin.movieapp.data.repository.product.datasourceImpl

import com.algokelvin.movieapp.data.api.ProductApiService
import com.algokelvin.movieapp.data.model.product.Product
import com.algokelvin.movieapp.data.repository.product.datasource.ProductRemoteDataSource
import retrofit2.Response

class ProductRemoteDataSourceImpl(
    private val service: ProductApiService,
    private val apiKey: String,
): ProductRemoteDataSource {
    override suspend fun getProducts(): Response<ArrayList<Product>> = service.getProductsList()
}