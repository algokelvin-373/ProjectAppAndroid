package com.algokelvin.movieapp.domain.repository

import com.algokelvin.movieapp.data.model.product.Product

interface ProductCategoryRepository {
    suspend fun getProductsSortByCategory(): List<Product>
}