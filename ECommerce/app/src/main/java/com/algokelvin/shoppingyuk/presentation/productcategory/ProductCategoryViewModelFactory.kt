package com.algokelvin.shoppingyuk.presentation.productcategory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.algokelvin.shoppingyuk.domain.usecase.GetProductsCategoryUseCase

@Suppress("UNCHECKED_CAST")
class ProductCategoryViewModelFactory(
    private val getProductsCategoryUseCase: GetProductsCategoryUseCase,
): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return ProductCategoryViewModel(getProductsCategoryUseCase) as T
    }
}