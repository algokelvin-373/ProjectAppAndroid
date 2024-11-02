package com.algokelvin.movieapp.presentation.product

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.algokelvin.movieapp.domain.usecase.GetProductsUseCase
import com.algokelvin.movieapp.domain.usecase.GetProfileFromDBUseCase

class ProductViewModel(
    private val getProductsUseCase: GetProductsUseCase,
    private val getProfileFromDBUseCase: GetProfileFromDBUseCase,
): ViewModel() {
    fun getProducts() = liveData {
        val productList = getProductsUseCase.execute()
        emit(productList)
    }
    fun getProfileFromDB(id: Int) = liveData {
        val profileUser = getProfileFromDBUseCase.execute(id)
        emit(profileUser)
    }
}