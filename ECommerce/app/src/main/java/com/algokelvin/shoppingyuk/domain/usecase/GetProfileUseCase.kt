package com.algokelvin.shoppingyuk.domain.usecase

import com.algokelvin.shoppingyuk.data.api.ResponseResults
import com.algokelvin.shoppingyuk.data.model.user.Login
import com.algokelvin.shoppingyuk.data.model.user.User
import com.algokelvin.shoppingyuk.domain.repository.LoginRepository

class GetProfileUseCase(private val loginRepository: LoginRepository) {
    suspend fun execute(login: Login): ResponseResults<User> = loginRepository.getUser(login)
}