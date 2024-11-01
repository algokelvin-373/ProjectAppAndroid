package com.algokelvin.movieapp.data.repository.productDetail.datasource

import com.algokelvin.movieapp.data.model.Product
import retrofit2.Response

interface ProductDetailRemoteDataSource {
    suspend fun getProductDetail(id: String): Response<Product>
}