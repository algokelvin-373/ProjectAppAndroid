package com.algokelvin.movieapp.data.repository.login

import android.util.Log
import com.algokelvin.movieapp.data.model.user.Login
import com.algokelvin.movieapp.data.model.user.Token
import com.algokelvin.movieapp.data.repository.login.datasource.LoginRemoteDataSource
import com.algokelvin.movieapp.domain.repository.LoginRepository

class LoginRepositoryImpl(private val remote: LoginRemoteDataSource): LoginRepository {
    override suspend fun login(login: Login): Token = loginProcess(login)

    private suspend fun loginProcess(login: Login): Token {
        lateinit var token: Token

        try {
            val response = remote.login(login)
            val body = response.body()
            if (body != null) {
                token = body
            }
        } catch (e: Exception) {
            Log.i("ALGOKELVIN", e.message.toString())
        }

        return token
    }
}