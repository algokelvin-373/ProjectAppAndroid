package com.algokelvin.app.model.mapper

import com.algokelvin.app.model.entity.Product
import com.algokelvin.app.model.uimodel.ProductUIModel
import com.algokelvin.app.model.uimodel.ProductsUIModel

fun List<Product>?.mapToUiModel() = ProductsUIModel(
    data = this?.map {
        it.mapToUiModel()
    }?: listOf()
)

fun Product?.mapToUiModel() = ProductUIModel(
    id = this?.id?: 0,
    name = this?.name?: "",
    price = this?.price?: 0
)