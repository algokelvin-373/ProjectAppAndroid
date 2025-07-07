package com.algokelvin.shoppingyuk.data.repository.login

import com.algokelvin.shoppingyuk.data.api.ResponseResults
import com.algokelvin.shoppingyuk.data.model.user.Login
import com.algokelvin.shoppingyuk.data.model.user.Token
import com.algokelvin.shoppingyuk.data.model.user.User
import com.algokelvin.shoppingyuk.data.repository.login.datasource.LoginLocalDataSource
import com.algokelvin.shoppingyuk.data.repository.login.datasource.LoginRemoteDataSource
import com.algokelvin.shoppingyuk.domain.repository.LoginRepository

class LoginRepositoryImpl(
    private val remote: LoginRemoteDataSource,
    private val local: LoginLocalDataSource,
): LoginRepository {
    override suspend fun login(login: Login): ResponseResults<Token> = loginProcess(login)

    override suspend fun getUser(login: Login): ResponseResults<User> = getProfileUser(login)

    override suspend fun getUserFromDB(id: Int): User = getProfileFromDB(id)

    private suspend fun loginProcess(login: Login): ResponseResults<Token> {
        val token: Token?

        try {
            val response = remote.login(login)
            if (response.isSuccessful) {
                val body = response.body()
                if (body != null) {
                    token = body
                    return ResponseResults(token)
                } else {
                    return ResponseResults(null, "Value Body is NULL")
                }
            } else {
                val errorMessage = response.errorBody()?.string() ?: "Unknown Error"
                return ResponseResults(null, errorMessage)
            }
        } catch (e: Exception) {
            return ResponseResults(null, e.message)
        }
    }

    private suspend fun getProfileUser(login: Login): ResponseResults<User> {
        val user: User?

        try {
            val response = remote.getAllUser()
            if (response.isSuccessful) {
                val body = response.body()
                if (body != null) {
                    user = body.find { it.username == login.username && it.password == login.password }
                    user?.let { saveProfile(it) }
                    return ResponseResults(user)
                } else {
                    return ResponseResults(null, "Value Body is NULL")
                }
            } else {
                val errorMessage = response.errorBody()?.string() ?: "Unknown Error"
                return ResponseResults(null, errorMessage)
            }
        } catch (e: Exception) {
            return ResponseResults(null, e.message)
        }
    }

    private suspend fun saveProfile(user: User) {
        try {
            local.saveUserToDB(user)
        } catch (e: Exception) {
            //Log.i("ALGOKELVIN_DEBUG", e.message.toString())
        }
    }

    private suspend fun getProfileFromDB(id: Int): User {
        lateinit var user: User

        try {
            user = local.getUserFromDB(id)
        } catch (e: Exception) {
            //Log.i("ALGOKELVIN_DEBUG", e.message.toString())
        }

        return user
    }

}