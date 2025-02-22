package com.algokelvin.shoppingyuk.presentation.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.algokelvin.shoppingyuk.domain.usecase.GetProfileUseCase
import com.algokelvin.shoppingyuk.domain.usecase.LoginUseCase

@Suppress("UNCHECKED_CAST")
class LoginViewModelFactory(
    private val loginUseCase: LoginUseCase,
    private val getProfileUseCase: GetProfileUseCase,
): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return LoginViewModel(loginUseCase, getProfileUseCase) as T
    }
}