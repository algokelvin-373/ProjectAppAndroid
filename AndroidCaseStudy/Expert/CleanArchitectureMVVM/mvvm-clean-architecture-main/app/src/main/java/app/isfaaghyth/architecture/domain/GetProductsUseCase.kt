package app.isfaaghyth.architecture.domain

import app.isfaaghyth.architecture.base.UseCase
import app.isfaaghyth.architecture.data.Resource
import app.isfaaghyth.architecture.data.mapper.mapToUiModel
import app.isfaaghyth.architecture.data.repository.ProductRepository
import app.isfaaghyth.architecture.ui.uimodel.ProductsUIModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.map

class GetProductsUseCase constructor(
    private val repository: ProductRepository,
    dispatcher: CoroutineDispatcher
) : UseCase<Unit, ProductsUIModel>(dispatcher) {

    override fun execute(param: Unit): Flow<Resource<ProductsUIModel>> {
        return repository.products().map {
            when(it.status) {
                Resource.Status.SUCCESS -> {
                    Resource.success(it.data.mapToUiModel())
                }
                else -> {
                    Resource.error(it.message?: "")
                }
            }
        }
    }

}