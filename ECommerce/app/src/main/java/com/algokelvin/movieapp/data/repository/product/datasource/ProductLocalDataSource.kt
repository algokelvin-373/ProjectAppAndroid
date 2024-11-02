package com.algokelvin.movieapp.data.repository.product.datasource

import com.algokelvin.movieapp.data.model.Product

interface ProductLocalDataSource {
    suspend fun getProductsFromDB(): List<Product>
    suspend fun saveProductsToDB(products: List<Product>)
    suspend fun clearAll()
}