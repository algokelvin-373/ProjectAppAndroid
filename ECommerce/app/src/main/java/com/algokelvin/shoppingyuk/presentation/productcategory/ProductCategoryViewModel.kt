package com.algokelvin.shoppingyuk.presentation.productcategory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.algokelvin.shoppingyuk.domain.usecase.GetProductsCategoryUseCase

class ProductCategoryViewModel(
    private val getProductsCategoryUseCase: GetProductsCategoryUseCase,
): ViewModel() {

    fun getProductsSortByCategory() = liveData {
        val productsCategory = getProductsCategoryUseCase.execute()
        emit(productsCategory)
    }

}