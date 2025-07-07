package com.algokelvin.shoppingyuk.data.repository.product.datasourceImpl

import com.algokelvin.shoppingyuk.data.db.ProductDao
import com.algokelvin.shoppingyuk.data.model.product.Product
import com.algokelvin.shoppingyuk.data.repository.product.datasource.ProductLocalDataSource
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ProductLocalDataSourceImpl(private val productDao: ProductDao): ProductLocalDataSource {
    override suspend fun getProductsFromDB(): List<Product> = productDao.getAllProducts()
    override suspend fun getProductByIdFromDB(id: Int): Product = productDao.getProductById(id)

    override suspend fun saveProductsToDB(products: List<Product>) {
        CoroutineScope(Dispatchers.IO).launch {
            productDao.saveProducts(products)
        }
    }

    override suspend fun clearAll() {
        CoroutineScope(Dispatchers.IO).launch {
            productDao.deleteAllProducts()
        }
    }
}