package com.algokelvin.movieapp.data.repository.productCategory

import com.algokelvin.movieapp.data.model.product.Product
import com.algokelvin.movieapp.data.repository.productCategory.datasource.ProductCategoryRemoteDataSource
import com.algokelvin.movieapp.domain.repository.ProductCategoryRepository

class ProductCategoryRepositoryImpl(
    private val remote: ProductCategoryRemoteDataSource
): ProductCategoryRepository {

    override suspend fun getProductsSortByCategory(): List<Product> = getProductsFromAPI()

    private suspend fun getProductsFromAPI(): List<Product> {
        lateinit var productList: ArrayList<Product>

        try {
            val response = remote.getProductsSortByCategory()
            val body = response.body()
            if (body != null) {
                productList = body
            }
        } catch (e: Exception) {
            //Log.i("ALGOKELVIN", e.message.toString())
        }

        return productList.sortedBy { it.category }
    }

}