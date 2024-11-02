package com.algokelvin.movieapp.presentation.productcategory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.algokelvin.movieapp.domain.usecase.GetProductsCategoryUseCase

class ProductCategoryViewModel(
    private val getProductsCategoryUseCase: GetProductsCategoryUseCase,
): ViewModel() {

    fun getProductsSortByCategory() = liveData {
        val productsCategory = getProductsCategoryUseCase.execute()
        emit(productsCategory)
    }

}