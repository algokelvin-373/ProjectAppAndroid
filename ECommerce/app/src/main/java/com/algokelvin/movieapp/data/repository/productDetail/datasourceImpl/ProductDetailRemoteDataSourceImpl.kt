package com.algokelvin.movieapp.data.repository.productDetail.datasourceImpl

import com.algokelvin.movieapp.data.api.ProductApiService
import com.algokelvin.movieapp.data.model.Product
import com.algokelvin.movieapp.data.repository.productDetail.datasource.ProductDetailRemoteDataSource
import retrofit2.Response

class ProductDetailRemoteDataSourceImpl(
    private val service: ProductApiService
): ProductDetailRemoteDataSource {
    override suspend fun getProductDetail(id: String): Response<Product> = service.getProductDetailList(id)
}