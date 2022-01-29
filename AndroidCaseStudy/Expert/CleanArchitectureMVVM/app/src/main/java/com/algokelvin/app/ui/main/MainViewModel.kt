package com.algokelvin.app.ui.main

import androidx.lifecycle.*
import com.algokelvin.app.domain.*

class MainViewModel(
    private val getProductById: GetProductByIdUseCase,
    getProducts: GetProductsUseCase
): ViewModel() {

    private val _productId = MutableLiveData<Int>()
    val products = getProducts(Unit).asLiveData()
    val productById = _productId.switchMap { id ->
        getProductById(id).asLiveData()
    }

    fun productId(id: Int) {
        _productId.value = id
    }

}