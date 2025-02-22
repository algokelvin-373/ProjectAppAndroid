package com.algokelvin.shoppingyuk.presentation.product

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.algokelvin.shoppingyuk.domain.usecase.GetProductsUseCase

class ProductViewModel(
    private val getProductsUseCase: GetProductsUseCase,
): ViewModel() {
    fun getProducts() = liveData {
        val productList = getProductsUseCase.execute()
        emit(productList)
    }
}