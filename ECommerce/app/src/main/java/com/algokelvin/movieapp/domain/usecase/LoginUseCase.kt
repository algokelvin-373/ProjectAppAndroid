package com.algokelvin.movieapp.domain.usecase

import com.algokelvin.movieapp.data.model.Login
import com.algokelvin.movieapp.data.model.Token
import com.algokelvin.movieapp.domain.repository.LoginRepository

class LoginUseCase(private val loginRepository: LoginRepository) {
    suspend fun execute(login: Login): Token = loginRepository.login(login)
}