package com.algokelvin.app.domain

import com.algokelvin.app.base.UseCase
import com.algokelvin.app.model.Resource
import com.algokelvin.app.model.mapper.mapToUiModel
import com.algokelvin.app.model.repository.ProductRepository
import com.algokelvin.app.model.uimodel.ProductUIModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GetProductByIdUseCase(
    private val repository: ProductRepository,
    dispatcher: CoroutineDispatcher
): UseCase<Int, ProductUIModel>(dispatcher) {

    override fun execute(param: Int): Flow<Resource<ProductUIModel>> {
        return repository.productBy(param).map {
            Resource.success(it.mapToUiModel())
        }
    }

}