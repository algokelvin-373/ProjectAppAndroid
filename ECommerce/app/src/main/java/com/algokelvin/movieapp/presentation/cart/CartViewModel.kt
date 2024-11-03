package com.algokelvin.movieapp.presentation.cart

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.algokelvin.movieapp.domain.usecase.DeleteProductInCartUseCase
import com.algokelvin.movieapp.domain.usecase.GetCartByUserIdUseCase
import com.algokelvin.movieapp.domain.usecase.UpdateCountProductInCartUseCase

class CartViewModel(
    private val getCartByUserIdUseCase: GetCartByUserIdUseCase,
    private val updateCountProductInCartUseCase: UpdateCountProductInCartUseCase,
    private val deleteProductInCartUseCase: DeleteProductInCartUseCase,
): ViewModel() {
    fun getCartByUserId(userId: Int) = liveData {
        val listCart = getCartByUserIdUseCase.execute(userId)
        emit(listCart)
    }
    fun updateCountProduct(userId: Int, productInt: Int, count: Int) = liveData {
        val statusUpdateCountProduct = updateCountProductInCartUseCase
            .execute(userId, productInt, count)
        emit(statusUpdateCountProduct)
    }
    fun deleteProduct(userId: Int, productInt: Int) = liveData {
        val statusDeleteProduct = deleteProductInCartUseCase.execute(userId, productInt)
        emit(statusDeleteProduct)
    }
}