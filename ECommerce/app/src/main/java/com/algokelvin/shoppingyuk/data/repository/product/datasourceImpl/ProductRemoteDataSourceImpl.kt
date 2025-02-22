package com.algokelvin.shoppingyuk.data.repository.product.datasourceImpl

import com.algokelvin.shoppingyuk.data.api.ProductApiService
import com.algokelvin.shoppingyuk.data.model.product.Product
import com.algokelvin.shoppingyuk.data.repository.product.datasource.ProductRemoteDataSource
import retrofit2.Response

class ProductRemoteDataSourceImpl(
    private val service: ProductApiService,
): ProductRemoteDataSource {
    override suspend fun getProducts(): Response<ArrayList<Product>> = service.getProductsList()
}