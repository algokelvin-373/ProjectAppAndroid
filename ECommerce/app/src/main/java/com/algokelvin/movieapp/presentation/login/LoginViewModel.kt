package com.algokelvin.movieapp.presentation.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.algokelvin.movieapp.data.model.user.Login
import com.algokelvin.movieapp.domain.usecase.LoginUseCase

class LoginViewModel(private val loginUseCase: LoginUseCase): ViewModel() {
    fun login(login: Login) = liveData {
        val token = loginUseCase.execute(login)
        emit(token)
    }
}