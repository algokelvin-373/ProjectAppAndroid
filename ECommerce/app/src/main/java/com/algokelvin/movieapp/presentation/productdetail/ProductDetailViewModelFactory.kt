package com.algokelvin.movieapp.presentation.productdetail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.algokelvin.movieapp.domain.usecase.AddProductToCartUseCase
import com.algokelvin.movieapp.domain.usecase.GetProductDetailUseCase

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