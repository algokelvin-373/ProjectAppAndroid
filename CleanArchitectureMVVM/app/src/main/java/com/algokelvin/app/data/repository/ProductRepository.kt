package com.algokelvin.app.data.repository

import com.algokelvin.app.data.Resource
import com.algokelvin.app.data.entity.Product
import kotlinx.coroutines.flow.Flow

interface ProductRepository {
    fun products(): Flow<Resource<List<Product>>>
    fun productById(id: Int): Flow<Product>
}