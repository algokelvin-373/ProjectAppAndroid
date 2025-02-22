package com.algokelvin.shoppingyuk.presentation.checkout

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.algokelvin.shoppingyuk.domain.usecase.CheckoutUseCase
import com.algokelvin.shoppingyuk.domain.usecase.GetCartByUserIdUseCase

class CheckoutViewModel(
    private val getCartByUserIdUseCase: GetCartByUserIdUseCase,
    private val checkoutUseCase: CheckoutUseCase,
): ViewModel() {
    fun getCartByUserId(userId: Int) = liveData {
        val listCart = getCartByUserIdUseCase.execute(userId)
        emit(listCart)
    }
    fun confirmCheckout(userId: Int) = liveData {
        val statusConfirm = checkoutUseCase.execute(userId)
        emit(statusConfirm)
    }
}