package com.algokelvin.shoppingyuk.data.repository.product.datasource

import com.algokelvin.shoppingyuk.data.model.product.Product

interface ProductLocalDataSource {
    suspend fun getProductsFromDB(): List<Product>
    suspend fun getProductByIdFromDB(id: Int): Product
    suspend fun saveProductsToDB(products: List<Product>)
    suspend fun clearAll()
}