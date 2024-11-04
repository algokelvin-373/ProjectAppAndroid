package com.algokelvin.movieapp.data.repository.productDetail

import android.util.Log
import com.algokelvin.movieapp.data.model.product.Product
import com.algokelvin.movieapp.data.repository.productDetail.datasource.ProductDetailRemoteDataSource
import com.algokelvin.movieapp.domain.repository.ProductDetailRepository

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
            Log.i("ALGOKELVIN", e.message.toString())
        }

        return product
    }

}