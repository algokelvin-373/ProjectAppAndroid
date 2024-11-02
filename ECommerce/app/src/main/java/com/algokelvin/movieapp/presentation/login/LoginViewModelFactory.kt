package com.algokelvin.movieapp.presentation.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.algokelvin.movieapp.domain.usecase.GetProductsUseCase
import com.algokelvin.movieapp.domain.usecase.LoginUseCase
import com.algokelvin.movieapp.presentation.product.ProductViewModel

class LoginViewModelFactory(
    private val loginUseCase: LoginUseCase
): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return LoginViewModel(loginUseCase) as T
    }
}