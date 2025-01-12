package com.algokelvin.shoppingyuk.presentation.productdetail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.algokelvin.shoppingyuk.domain.usecase.AddProductToCartUseCase
import com.algokelvin.shoppingyuk.domain.usecase.GetProductDetailUseCase

@Suppress("UNCHECKED_CAST")
class ProductDetailViewModelFactory(
    private val getProductDetailUseCase: GetProductDetailUseCase,
    private val addProductToCartUseCase: AddProductToCartUseCase,
): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return ProductDetailViewModel(
            getProductDetailUseCase,
            addProductToCartUseCase,
        ) as T
    }
}