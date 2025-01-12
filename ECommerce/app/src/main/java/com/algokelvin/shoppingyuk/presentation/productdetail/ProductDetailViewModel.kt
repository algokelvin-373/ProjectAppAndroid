package com.algokelvin.shoppingyuk.presentation.productdetail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.algokelvin.shoppingyuk.data.model.cart.CartDB
import com.algokelvin.shoppingyuk.domain.usecase.AddProductToCartUseCase
import com.algokelvin.shoppingyuk.domain.usecase.GetProductDetailUseCase

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