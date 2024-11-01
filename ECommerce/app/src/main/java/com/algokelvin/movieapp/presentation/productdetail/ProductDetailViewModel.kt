package com.algokelvin.movieapp.presentation.productdetail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.algokelvin.movieapp.domain.usecase.GetProductDetailUseCase

class ProductDetailViewModel(
    private val getProductDetailUseCase: GetProductDetailUseCase,
): ViewModel() {

    fun getProductDetail(id: String) = liveData {
        val productDetail = getProductDetailUseCase.execute(id)
        emit(productDetail)
    }

}