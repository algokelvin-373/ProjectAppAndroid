package com.algokelvin.movieapp.data.repository.login.datasourceImpl

import com.algokelvin.movieapp.data.api.ProductApiService
import com.algokelvin.movieapp.data.model.user.Login
import com.algokelvin.movieapp.data.model.user.Token
import com.algokelvin.movieapp.data.repository.login.datasource.LoginRemoteDataSource
import retrofit2.Response

class LoginRemoteDataSourceImpl(
    private val productApiService: ProductApiService
): LoginRemoteDataSource {
    override suspend fun login(login: Login): Response<Token> = productApiService.login(login)
}