package app.isfaaghyth.architecture.data.datasource.remote

import app.isfaaghyth.architecture.data.entity.Product

interface ProductServices {
    fun products(): List<Product>
    fun productBy(id: Int): Product
}