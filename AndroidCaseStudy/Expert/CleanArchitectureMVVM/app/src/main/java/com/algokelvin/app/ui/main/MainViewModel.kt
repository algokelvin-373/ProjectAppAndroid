package com.algokelvin.app.ui.main

import androidx.lifecycle.*
import com.algokelvin.app.domain.GetProductByIdUseCase
import com.algokelvin.app.domain.GetProductsUseCase
import com.algokelvin.app.model.Resource
import com.algokelvin.app.model.uimodel.ProductUIModel
import com.algokelvin.app.model.uimodel.ProductsUIModel

class MainViewModel(
    private val getProductById: GetProductByIdUseCase,
    getProducts: GetProductsUseCase
): ViewModel() {

    private val _productId = MutableLiveData<Int>()

    val products: LiveData<Resource<ProductsUIModel>> = getProducts(Unit).asLiveData()
    val productById: LiveData<Resource<ProductUIModel>> = _productId.switchMap { id ->
        getProductById(id).asLiveData()
    }

    fun productId(id: Int) {
        _productId.value = id
    }

}