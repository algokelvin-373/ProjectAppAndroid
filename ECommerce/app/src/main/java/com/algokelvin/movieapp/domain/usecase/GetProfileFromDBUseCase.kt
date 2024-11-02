package com.algokelvin.movieapp.domain.usecase

import com.algokelvin.movieapp.data.model.user.User
import com.algokelvin.movieapp.domain.repository.LoginRepository

class GetProfileFromDBUseCase(private val loginRepository: LoginRepository) {
    suspend fun execute(id: Int): User = loginRepository.getUserFromDB(id)
}