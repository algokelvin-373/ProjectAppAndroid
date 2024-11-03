package com.algokelvin.movieapp.presentation.cart

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.algokelvin.movieapp.domain.usecase.DeleteProductInCartUseCase
import com.algokelvin.movieapp.domain.usecase.GetCartByUserIdUseCase
import com.algokelvin.movieapp.domain.usecase.GetProfileUseCase
import com.algokelvin.movieapp.domain.usecase.LoginUseCase
import com.algokelvin.movieapp.domain.usecase.UpdateCountProductInCartUseCase

class CartViewModelFactory(
    private val getCartByUserIdUseCase: GetCartByUserIdUseCase,
    private val updateCountProductInCartUseCase: UpdateCountProductInCartUseCase,
    private val deleteProductInCartUseCase: DeleteProductInCartUseCase,
): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return CartViewModel(
            getCartByUserIdUseCase,
            updateCountProductInCartUseCase,
            deleteProductInCartUseCase
        ) as T
    }
}