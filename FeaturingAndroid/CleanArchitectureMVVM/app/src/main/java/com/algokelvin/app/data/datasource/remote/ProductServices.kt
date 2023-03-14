package com.algokelvin.app.data.datasource.remote

import com.algokelvin.app.data.entity.Product

interface ProductServices {
    fun products(): List<Product>
    fun productById(id: Int): Product
}