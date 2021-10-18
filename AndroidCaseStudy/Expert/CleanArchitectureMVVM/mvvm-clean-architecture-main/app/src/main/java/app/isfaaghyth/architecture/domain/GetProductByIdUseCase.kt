package app.isfaaghyth.architecture.domain

import app.isfaaghyth.architecture.base.UseCase
import app.isfaaghyth.architecture.data.Resource
import app.isfaaghyth.architecture.data.mapper.mapToUiModel
import app.isfaaghyth.architecture.data.repository.ProductRepository
import app.isfaaghyth.architecture.ui.uimodel.ProductUIModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GetProductByIdUseCase constructor(
    private val repository: ProductRepository,
    dispatcher: CoroutineDispatcher
) : UseCase<Int, ProductUIModel>(dispatcher) {

    override fun execute(param: Int): Flow<Resource<ProductUIModel>> {
        return repository.productBy(param).map {
            Resource.success(it.mapToUiModel())
        }
    }

}