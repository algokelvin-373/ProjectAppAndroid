package com.algokelvin.movieapp.domain.repository

import com.algokelvin.movieapp.data.model.product.Product

interface ProductDetailRepository {
    suspend fun getProductDetail(id: String): Product?
}