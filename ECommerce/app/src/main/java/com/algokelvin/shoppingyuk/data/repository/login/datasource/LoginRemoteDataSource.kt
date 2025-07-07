package com.algokelvin.shoppingyuk.data.repository.login.datasource

import com.algokelvin.shoppingyuk.data.model.user.Login
import com.algokelvin.shoppingyuk.data.model.user.Token
import com.algokelvin.shoppingyuk.data.model.user.User
import retrofit2.Response

interface LoginRemoteDataSource {
    suspend fun login(login: Login): Response<Token>
    suspend fun getAllUser(): Response<ArrayList<User>>
}