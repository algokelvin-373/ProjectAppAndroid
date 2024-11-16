package com.algokelvin.shoppingyuk.data.repository.productDetail.datasourceImpl

import com.algokelvin.shoppingyuk.data.api.ProductApiService
import com.algokelvin.shoppingyuk.data.model.product.Product
import com.algokelvin.shoppingyuk.data.repository.productDetail.datasource.ProductDetailRemoteDataSource
import retrofit2.Response

class ProductDetailRemoteDataSourceImpl(
    private val service: ProductApiService
): ProductDetailRemoteDataSource {
    override suspend fun getProductDetail(id: String): Response<Product> = service.getProductDetailList(id)
}