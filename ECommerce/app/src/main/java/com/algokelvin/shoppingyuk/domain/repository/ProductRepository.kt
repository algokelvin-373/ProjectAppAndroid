package com.algokelvin.shoppingyuk.domain.repository

import com.algokelvin.shoppingyuk.data.model.product.Product

interface ProductRepository {
    suspend fun getProducts(): List<Product>?
    suspend fun updateProducts(): List<Product>?
}