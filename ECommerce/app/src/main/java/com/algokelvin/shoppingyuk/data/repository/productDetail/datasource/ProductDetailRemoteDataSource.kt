package com.algokelvin.shoppingyuk.data.repository.productDetail.datasource

import com.algokelvin.shoppingyuk.data.model.product.Product
import retrofit2.Response

interface ProductDetailRemoteDataSource {
    suspend fun getProductDetail(id: String): Response<Product>
}