package com.algokelvin.movieapp.domain.repository

import com.algokelvin.movieapp.data.model.Product

interface ProductCategoryRepository {
    suspend fun getProductsSortByCategory(): List<Product>
}