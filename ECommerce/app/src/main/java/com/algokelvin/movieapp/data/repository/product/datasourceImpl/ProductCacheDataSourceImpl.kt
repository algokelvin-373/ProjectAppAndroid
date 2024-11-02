package com.algokelvin.movieapp.data.repository.product.datasourceImpl

import com.algokelvin.movieapp.data.model.Product
import com.algokelvin.movieapp.data.repository.product.datasource.ProductCacheDataSource

class ProductCacheDataSourceImpl: ProductCacheDataSource {
    private var productList = ArrayList<Product>()

    override suspend fun getProductsFromCache(): List<Product> {
        return productList
    }

    override suspend fun saveProductsToCache(products: List<Product>) {
        productList.clear()
        productList = ArrayList(products)
    }
}