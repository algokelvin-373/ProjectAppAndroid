package com.algokelvin.movieapp.data.repository.login.datasource

import com.algokelvin.movieapp.data.model.user.User

interface LoginLocalDataSource {
    suspend fun saveUserToDB(user: User)
    suspend fun getUserFromDB(id: Int): User
}