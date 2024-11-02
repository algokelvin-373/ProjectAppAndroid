package com.algokelvin.movieapp.domain.usecase

import com.algokelvin.movieapp.data.api.ResponseResults
import com.algokelvin.movieapp.data.model.user.Login
import com.algokelvin.movieapp.data.model.user.Token
import com.algokelvin.movieapp.domain.repository.LoginRepository

class LoginUseCase(private val loginRepository: LoginRepository) {
    suspend fun execute(login: Login): ResponseResults<Token> = loginRepository.login(login)
}