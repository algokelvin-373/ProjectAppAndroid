package com.algokelvin.movieapp.presentation.productdetail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.algokelvin.movieapp.domain.usecase.GetProductDetailUseCase

class ProductDetailViewModelFactory(
    private val getProductDetailUseCase: GetProductDetailUseCase,
): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return ProductDetailViewModel(getProductDetailUseCase) as T
    }
}