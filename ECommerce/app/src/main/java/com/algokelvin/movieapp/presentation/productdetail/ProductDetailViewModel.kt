package com.algokelvin.movieapp.presentation.productdetail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.algokelvin.movieapp.data.model.cart.CartDB
import com.algokelvin.movieapp.domain.usecase.AddProductToCartUseCase
import com.algokelvin.movieapp.domain.usecase.GetProductDetailUseCase

class ProductDetailViewModel(
    private val getProductDetailUseCase: GetProductDetailUseCase,
    private val addProductToCartUseCase: AddProductToCartUseCase,
): ViewModel() {

    fun getProductDetail(id: String) = liveData {
        val productDetail = getProductDetailUseCase.execute(id)
        emit(productDetail)
    }

    fun addProductToCart(cartDB: CartDB) = liveData {
        val statusAddProductToCart = addProductToCartUseCase.execute(cartDB)
        emit(statusAddProductToCart)
    }

}