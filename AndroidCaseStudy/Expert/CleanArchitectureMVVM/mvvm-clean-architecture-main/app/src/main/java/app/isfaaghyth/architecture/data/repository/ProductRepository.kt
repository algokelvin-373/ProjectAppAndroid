package app.isfaaghyth.architecture.data.repository

import app.isfaaghyth.architecture.data.NetworkBoundResource
import app.isfaaghyth.architecture.data.Resource
import app.isfaaghyth.architecture.data.datasource.local.ProductDao
import app.isfaaghyth.architecture.data.datasource.remote.ProductServices
import app.isfaaghyth.architecture.data.entity.Product
import kotlinx.coroutines.flow.Flow

interface ProductRepository {
    fun products(): Flow<Resource<List<Product>>>
    fun productBy(id: Int): Flow<Product>
}

class ProductRepositoryImpl constructor(
    private val localSource: ProductDao,
    private val remoteSource: ProductServices
) : ProductRepository {

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