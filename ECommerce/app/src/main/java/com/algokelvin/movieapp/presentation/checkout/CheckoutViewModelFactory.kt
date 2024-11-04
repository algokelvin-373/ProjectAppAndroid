package com.algokelvin.movieapp.presentation.checkout

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.algokelvin.movieapp.domain.usecase.CheckoutUseCase
import com.algokelvin.movieapp.domain.usecase.GetCartByUserIdUseCase

@Suppress("UNCHECKED_CAST")
class CheckoutViewModelFactory(
    private val getCartByUserIdUseCase: GetCartByUserIdUseCase,
    private val checkoutUseCase: CheckoutUseCase,
): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return CheckoutViewModel(
            getCartByUserIdUseCase,
            checkoutUseCase
        ) as T
    }
}