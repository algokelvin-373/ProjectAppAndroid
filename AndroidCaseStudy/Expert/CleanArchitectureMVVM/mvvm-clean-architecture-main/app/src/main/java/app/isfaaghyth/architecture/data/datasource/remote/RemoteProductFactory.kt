package app.isfaaghyth.architecture.data.datasource.remote

import app.isfaaghyth.architecture.data.entity.Product

/*
* This is mocking remote data source,
* it should be request the data with network,
* using okhttp or retrofit through ProductServices.
* */
class RemoteProductFactory : ProductServices {

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