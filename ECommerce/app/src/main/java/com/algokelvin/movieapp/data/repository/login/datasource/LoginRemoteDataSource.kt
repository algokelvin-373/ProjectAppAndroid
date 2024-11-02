package com.algokelvin.movieapp.data.repository.login.datasource

import com.algokelvin.movieapp.data.api.ResponseResults
import com.algokelvin.movieapp.data.model.user.Login
import com.algokelvin.movieapp.data.model.user.Token
import com.algokelvin.movieapp.data.model.user.User
import retrofit2.Response

interface LoginRemoteDataSource {
    suspend fun login(login: Login): Response<Token>
    suspend fun getAllUser(): Response<ArrayList<User>>
}