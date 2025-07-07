package com.algokelvin.shoppingyuk.data.repository.productDetail

import com.algokelvin.shoppingyuk.data.model.product.Product
import com.algokelvin.shoppingyuk.data.repository.productDetail.datasource.ProductDetailRemoteDataSource
import com.algokelvin.shoppingyuk.domain.repository.ProductDetailRepository

class ProductDetailRepositoryImpl(
    private val remote: ProductDetailRemoteDataSource,
): ProductDetailRepository {

    override suspend fun getProductDetail(id: String): Product = getProductDetailFromAPI(id)

    private suspend fun getProductDetailFromAPI(id: String): Product {
        lateinit var product: Product

        try {
            val response = remote.getProductDetail(id)
            val body = response.body()
            if (body != null) {
                product = body
            }
        } catch (e: Exception) {
            //Log.i("ALGOKELVIN", e.message.toString())
        }

        return product
    }

}