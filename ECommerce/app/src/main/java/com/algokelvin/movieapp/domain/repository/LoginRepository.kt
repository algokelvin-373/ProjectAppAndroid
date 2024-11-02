package com.algokelvin.movieapp.domain.repository

import com.algokelvin.movieapp.data.api.ResponseResults
import com.algokelvin.movieapp.data.model.user.Login
import com.algokelvin.movieapp.data.model.user.Token
import com.algokelvin.movieapp.data.model.user.User

interface LoginRepository {
    suspend fun login(login: Login): ResponseResults<Token>
    suspend fun getUser(login: Login): ResponseResults<User>
    suspend fun getUserFromDB(id: Int): User
}