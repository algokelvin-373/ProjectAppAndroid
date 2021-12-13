package com.algokelvin.app.ui.main

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.algokelvin.app.data.Resource
import com.algokelvin.app.domain.GetProductsUseCase
import com.algokelvin.app.ui.uimodel.ProductsUIModel

class MainViewModel(getProduct: GetProductsUseCase): ViewModel() {
    private val idProduct = MutableLiveData<Int>()
    val products: LiveData<Resource<ProductsUIModel>> = getProduct(Unit).asLiveData()

    fun productId(id: Int) {
        idProduct.value = id
    }

}