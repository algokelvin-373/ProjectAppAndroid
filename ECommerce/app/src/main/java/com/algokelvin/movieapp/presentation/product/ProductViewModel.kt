package com.algokelvin.movieapp.presentation.product

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.algokelvin.movieapp.domain.usecase.GetProductsUseCase

class ProductViewModel(
    private val getProductsUseCase: GetProductsUseCase,
): ViewModel() {
    fun getProducts() = liveData {
        val productList = getProductsUseCase.execute()
        emit(productList)
    }
}