package com.algokelvin.shoppingyuk.domain.repository

import com.algokelvin.shoppingyuk.data.model.product.Product

interface ProductCategoryRepository {
    suspend fun getProductsSortByCategory(): List<Product>
}