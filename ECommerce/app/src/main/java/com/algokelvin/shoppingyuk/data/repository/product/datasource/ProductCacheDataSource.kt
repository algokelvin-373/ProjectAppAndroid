package com.algokelvin.shoppingyuk.data.repository.product.datasource

import com.algokelvin.shoppingyuk.data.model.product.Product

interface ProductCacheDataSource {
    suspend fun getProductsFromCache(): List<Product>
    suspend fun saveProductsToCache(products: List<Product>)
}