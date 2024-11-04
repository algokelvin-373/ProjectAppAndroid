package com.algokelvin.shoppingyuk.data.repository.login.datasource

import com.algokelvin.shoppingyuk.data.model.user.User

interface LoginLocalDataSource {
    suspend fun saveUserToDB(user: User)
    suspend fun getUserFromDB(id: Int): User
}