package com.algokelvin.shoppingyuk.domain.repository

import com.algokelvin.shoppingyuk.data.api.ResponseResults
import com.algokelvin.shoppingyuk.data.model.user.Login
import com.algokelvin.shoppingyuk.data.model.user.Token
import com.algokelvin.shoppingyuk.data.model.user.User

interface LoginRepository {
    suspend fun login(login: Login): ResponseResults<Token>
    suspend fun getUser(login: Login): ResponseResults<User>
    suspend fun getUserFromDB(id: Int): User
}