package com.algokelvin.shoppingyuk.presentation.product

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.algokelvin.shoppingyuk.domain.usecase.GetProductsUseCase

@Suppress("UNCHECKED_CAST")
class ProductViewModelFactory(
    private val getProductsUseCase: GetProductsUseCase,
): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return ProductViewModel(getProductsUseCase) as T
    }
}