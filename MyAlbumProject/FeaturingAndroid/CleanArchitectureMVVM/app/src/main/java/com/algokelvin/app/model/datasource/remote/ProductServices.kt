package com.algokelvin.app.model.datasource.remote

import com.algokelvin.app.model.entity.Product

interface ProductServices {
    fun products(): List<Product>
    fun productBy(id: Int): Product
}