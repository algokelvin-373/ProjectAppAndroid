package com.algokelvin.movieapp.data.repository.login.datasourceImpl

import com.algokelvin.movieapp.data.db.UserDao
import com.algokelvin.movieapp.data.model.user.User
import com.algokelvin.movieapp.data.repository.login.datasource.LoginLocalDataSource
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class LoginLocalDataSourceImpl(private val userDao: UserDao): LoginLocalDataSource {
    override suspend fun saveUserToDB(user: User) {
        CoroutineScope(Dispatchers.IO).launch {
            userDao.saveUser(user)
        }
    }

    override suspend fun getUserFromDB(id: Int): User = userDao.getUser(id)
}