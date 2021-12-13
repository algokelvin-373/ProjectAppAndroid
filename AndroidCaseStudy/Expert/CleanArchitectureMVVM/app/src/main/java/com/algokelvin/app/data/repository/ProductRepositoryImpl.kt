package com.algokelvin.app.data.repository

import com.algokelvin.app.data.NetworkBoundResource
import com.algokelvin.app.data.Resource
import com.algokelvin.app.data.datasource.local.ProductDao
import com.algokelvin.app.data.datasource.remote.ProductServices
import com.algokelvin.app.data.entity.Product
import kotlinx.coroutines.flow.Flow

class ProductRepositoryImpl(private val localSource: ProductDao, private val remoteSource: ProductServices): ProductRepository {

    override fun products(): Flow<Resource<List<Product>>> {
        return NetworkBoundResource(
            query = { localSource.products() },
            fetch = { remoteSource.products() },
            saveFetchResult = { localSource.insertAll(it) }
        ).asFlow()
    }

    override fun productById(id: Int): Flow<Product> {
        return localSource.productBy(id)
    }
}