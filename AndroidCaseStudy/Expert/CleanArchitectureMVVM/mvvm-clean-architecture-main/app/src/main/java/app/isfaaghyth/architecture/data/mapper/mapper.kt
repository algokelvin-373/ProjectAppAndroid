package app.isfaaghyth.architecture.data.mapper

import app.isfaaghyth.architecture.data.entity.Product
import app.isfaaghyth.architecture.ui.uimodel.ProductUIModel
import app.isfaaghyth.architecture.ui.uimodel.ProductsUIModel

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