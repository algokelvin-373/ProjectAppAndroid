package com.algokelvin.shoppingyuk.domain.repository

import com.algokelvin.shoppingyuk.data.model.product.Product

interface ProductDetailRepository {
    suspend fun getProductDetail(id: String): Product?
}