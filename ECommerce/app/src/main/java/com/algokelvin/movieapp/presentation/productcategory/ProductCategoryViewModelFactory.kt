package com.algokelvin.movieapp.presentation.productcategory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.algokelvin.movieapp.domain.usecase.GetProductsCategoryUseCase

class ProductCategoryViewModelFactory(
    private val getProductsCategoryUseCase: GetProductsCategoryUseCase,
): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return ProductCategoryViewModel(getProductsCategoryUseCase) as T
    }
}