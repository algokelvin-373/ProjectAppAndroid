package com.algokelvin.movieapp.domain.repository

import com.algokelvin.movieapp.data.model.Login
import com.algokelvin.movieapp.data.model.Token

interface LoginRepository {
    suspend fun login(login: Login): Token
}