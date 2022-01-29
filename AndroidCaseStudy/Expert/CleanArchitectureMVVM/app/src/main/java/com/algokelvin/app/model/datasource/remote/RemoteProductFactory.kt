package com.algokelvin.app.model.datasource.remote

import com.algokelvin.app.model.entity.Product

class RemoteProductFactory: ProductServices {

    private val products = listOf(
        Product(id = 1, name = "iPhone", price = 1000),
        Product(id = 2, name = "Android", price = 2000),
        Product(id = 3, name = "Huawei", price = 3000),
        Product(id = 4, name = "Xiaomi", price = 4000),
        Product(id = 5, name = "Samsung", price = 5000),
    )

    override fun products(): List<Product> {
        return products
    }

    override fun productBy(id: Int): Product {
        return products.first { it.id == id }
    }
}