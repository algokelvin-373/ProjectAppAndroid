package com.algokelvin.movieapp.data.repository.product

import com.algokelvin.movieapp.data.model.product.Product
import com.algokelvin.movieapp.data.repository.product.datasource.ProductCacheDataSource
import com.algokelvin.movieapp.data.repository.product.datasource.ProductLocalDataSource
import com.algokelvin.movieapp.data.repository.product.datasource.ProductRemoteDataSource
import com.algokelvin.movieapp.domain.repository.ProductRepository

class ProductRepositoryImpl(
    private val remote: ProductRemoteDataSource,
    private val local: ProductLocalDataSource,
    private val cache: ProductCacheDataSource,
): ProductRepository {

    override suspend fun getProducts(): List<Product> =  getProductsFromCache()

    override suspend fun updateProducts(): List<Product> {
        val newListOfProducts = getProductsFromAPI()
        local.clearAll()
        local.saveProductsToDB(newListOfProducts)
        cache.saveProductsToCache(newListOfProducts)
        return newListOfProducts
    }

    private suspend fun getProductsFromAPI(): List<Product> {
        lateinit var productList: ArrayList<Product>

        try {
            val response = remote.getProducts()
            val body = response.body()
            if (body != null) {
                productList = body
            }
        } catch (e: Exception) {
            //Log.i("ALGOKELVIN", e.message.toString())
        }

        return productList
    }

    private suspend fun getProductsFromDB(): List<Product> {
        lateinit var productList: List<Product>

        try {
            productList = local.getProductsFromDB()
        } catch (e: Exception) {
            //Log.i("ALGOKELVIN", e.message.toString())
        }

        if (productList.isNotEmpty()) {
            return productList
        } else {
            productList = getProductsFromAPI()
            local.saveProductsToDB(productList)
        }

        return productList
    }

    private suspend fun getProductsFromCache(): List<Product> {
        lateinit var productList: List<Product>

        try {
            productList = cache.getProductsFromCache()
        } catch (e: Exception) {
            //Log.i("ALGOKELVIN", e.message.toString())
        }

        if (productList.isNotEmpty()) {
            return productList
        } else {
            productList = getProductsFromDB()
            cache.saveProductsToCache(productList)
        }

        return productList
    }


}