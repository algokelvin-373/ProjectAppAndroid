package com.algokelvin.movieapp.domain.repository

import com.algokelvin.movieapp.data.model.user.Login
import com.algokelvin.movieapp.data.model.user.Token

interface LoginRepository {
    suspend fun login(login: Login): Token
}