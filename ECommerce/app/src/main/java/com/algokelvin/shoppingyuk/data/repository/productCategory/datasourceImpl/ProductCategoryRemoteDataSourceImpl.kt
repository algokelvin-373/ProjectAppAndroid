package com.algokelvin.shoppingyuk.data.repository.productCategory.datasourceImpl

import com.algokelvin.shoppingyuk.data.api.ProductApiService
import com.algokelvin.shoppingyuk.data.model.product.Product
import com.algokelvin.shoppingyuk.data.repository.productCategory.datasource.ProductCategoryRemoteDataSource
import retrofit2.Response

class ProductCategoryRemoteDataSourceImpl(
    private val service: ProductApiService,
): ProductCategoryRemoteDataSource {
    override suspend fun getProductsSortByCategory(): Response<ArrayList<Product>> = service.getProductsList()
}