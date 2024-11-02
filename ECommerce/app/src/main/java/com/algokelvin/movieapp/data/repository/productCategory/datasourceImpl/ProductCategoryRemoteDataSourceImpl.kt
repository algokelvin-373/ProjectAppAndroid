package com.algokelvin.movieapp.data.repository.productCategory.datasourceImpl

import com.algokelvin.movieapp.data.api.ProductApiService
import com.algokelvin.movieapp.data.model.product.Product
import com.algokelvin.movieapp.data.repository.productCategory.datasource.ProductCategoryRemoteDataSource
import retrofit2.Response

class ProductCategoryRemoteDataSourceImpl(
    private val service: ProductApiService,
): ProductCategoryRemoteDataSource {
    override suspend fun getProductsSortByCategory(): Response<ArrayList<Product>> = service.getProductsList()
}