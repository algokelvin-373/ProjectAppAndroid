package com.algokelvin.movieapp.data.repository.productCategory.datasource

import com.algokelvin.movieapp.data.model.product.Product
import retrofit2.Response

interface ProductCategoryRemoteDataSource {
    suspend fun getProductsSortByCategory(): Response<ArrayList<Product>>
}