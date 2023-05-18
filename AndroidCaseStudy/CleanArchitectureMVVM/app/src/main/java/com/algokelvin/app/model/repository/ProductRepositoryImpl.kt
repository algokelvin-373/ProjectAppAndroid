package com.algokelvin.app.model.repository

import com.algokelvin.app.model.NetworkBoundResource
import com.algokelvin.app.model.Resource
import com.algokelvin.app.model.datasource.local.ProductDao
import com.algokelvin.app.model.datasource.remote.ProductServices
import com.algokelvin.app.model.entity.Product
import kotlinx.coroutines.flow.Flow

class ProductRepositoryImpl(
    private val localSource: ProductDao,
    private val remoteSource: ProductServices
): ProductRepository {

    override fun products(): Flow<Resource<List<Product>>> {
        return NetworkBoundResource(
            query = { localSource.products() },
            fetch = { remoteSource.products() },
            saveFetchResult = { localSource.insertAll(it) }
        ).asFlow()
    }

    override fun productBy(id: Int): Flow<Product> {
        return localSource.productBy(id)
    }

}