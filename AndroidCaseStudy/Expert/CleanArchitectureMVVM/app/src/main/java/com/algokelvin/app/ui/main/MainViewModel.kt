package com.algokelvin.app.ui.main

import androidx.lifecycle.*
import com.algokelvin.app.domain.GetProductByIdUseCase
import com.algokelvin.app.domain.GetProductsUseCase

class MainViewModel(
    private val getProductById: GetProductByIdUseCase,
    getProducts: GetProductsUseCase
): ViewModel() {

    private val _productId = MutableLiveData<Int>()
    val products = getProducts(Unit).asLiveData()

    fun productId(id: Int) {
        _productId.value = id
    }

}