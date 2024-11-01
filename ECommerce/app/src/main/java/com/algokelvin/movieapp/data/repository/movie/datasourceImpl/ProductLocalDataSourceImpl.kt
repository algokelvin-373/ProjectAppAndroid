package com.algokelvin.movieapp.data.repository.movie.datasourceImpl

import com.algokelvin.movieapp.data.db.ProductDao
import com.algokelvin.movieapp.data.model.Product
import com.algokelvin.movieapp.data.repository.movie.datasource.ProductLocalDataSource
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ProductLocalDataSourceImpl(private val productDao: ProductDao): ProductLocalDataSource {
    override suspend fun getProductsFromDB(): List<Product> = productDao.getAllProducts()

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