package com.algokelvin.movieapp.data.repository.product.datasource

import com.algokelvin.movieapp.data.model.product.Product

interface ProductCacheDataSource {
    suspend fun getProductsFromCache(): List<Product>
    suspend fun saveProductsToCache(products: List<Product>)
}