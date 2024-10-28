package com.algokelvin.app.model.repository

import com.algokelvin.app.model.Resource
import com.algokelvin.app.model.entity.Product
import kotlinx.coroutines.flow.Flow

interface ProductRepository {
    fun products(): Flow<Resource<List<Product>>>
    fun productBy(id: Int): Flow<Product>
}