package com.algokelvin.app.domain

import com.algokelvin.app.base.UseCase
import com.algokelvin.app.data.Resource
import com.algokelvin.app.data.mapper.mapToUiModel
import com.algokelvin.app.data.repository.ProductRepository
import com.algokelvin.app.ui.uimodel.ProductsUIModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GetProductsUseCase (
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