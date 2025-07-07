package com.algokelvin.shoppingyuk.presentation.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.algokelvin.shoppingyuk.data.model.user.Login
import com.algokelvin.shoppingyuk.domain.usecase.GetProfileUseCase
import com.algokelvin.shoppingyuk.domain.usecase.LoginUseCase

class LoginViewModel(
    private val loginUseCase: LoginUseCase,
    private val getProfileUseCase: GetProfileUseCase,
): ViewModel() {
    fun login(login: Login) = liveData {
        val token = loginUseCase.execute(login)
        emit(token)
    }
    fun getProfile(login: Login) = liveData {
        val profile = getProfileUseCase.execute(login)
        emit(profile)
    }
}