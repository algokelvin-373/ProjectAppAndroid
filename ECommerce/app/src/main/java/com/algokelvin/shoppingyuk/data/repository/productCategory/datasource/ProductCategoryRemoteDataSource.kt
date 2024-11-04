package com.algokelvin.shoppingyuk.data.repository.productCategory.datasource

import com.algokelvin.shoppingyuk.data.model.product.Product
import retrofit2.Response

interface ProductCategoryRemoteDataSource {
    suspend fun getProductsSortByCategory(): Response<ArrayList<Product>>
}