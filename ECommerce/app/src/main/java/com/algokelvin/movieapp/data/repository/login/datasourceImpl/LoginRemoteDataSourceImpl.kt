package com.algokelvin.movieapp.data.repository.login.datasourceImpl

import com.algokelvin.movieapp.data.api.ProductApiService
import com.algokelvin.movieapp.data.model.Login
import com.algokelvin.movieapp.data.model.Token
import com.algokelvin.movieapp.data.repository.login.datasource.LoginRemoteDataSource
import retrofit2.Response

class LoginRemoteDataSourceImpl(
    private val productApiService: ProductApiService
): LoginRemoteDataSource {
    override suspend fun login(login: Login): Response<Token> = productApiService.login(login)
}