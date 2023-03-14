package com.algokelvin.app.data.mapper

import com.algokelvin.app.data.entity.Product
import com.algokelvin.app.ui.uimodel.ProductUIModel
import com.algokelvin.app.ui.uimodel.ProductsUIModel

fun Product?.mapToUiModel() = ProductUIModel(
    id = this?.id?: 0,
    name = this?.name?: "",
    price = this?.price?: 0
)

fun List<Product>?.mapToUiModel() = ProductsUIModel(
    data = this?.map {
        it.mapToUiModel()
    }?: listOf()
)