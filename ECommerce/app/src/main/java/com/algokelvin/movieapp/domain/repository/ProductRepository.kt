package com.algokelvin.movieapp.domain.repository

import com.algokelvin.movieapp.data.model.product.Product

interface ProductRepository {
    suspend fun getProducts(): List<Product>?
    suspend fun updateProducts(): List<Product>?
}