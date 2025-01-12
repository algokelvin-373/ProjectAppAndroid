package com.algokelvin.shoppingyuk.data.repository.login.datasourceImpl

import com.algokelvin.shoppingyuk.data.api.ProductApiService
import com.algokelvin.shoppingyuk.data.model.user.Login
import com.algokelvin.shoppingyuk.data.model.user.Token
import com.algokelvin.shoppingyuk.data.model.user.User
import com.algokelvin.shoppingyuk.data.repository.login.datasource.LoginRemoteDataSource
import retrofit2.Response

class LoginRemoteDataSourceImpl(
    private val productApiService: ProductApiService
): LoginRemoteDataSource {
    override suspend fun login(login: Login): Response<Token> = productApiService.login(login)
    override suspend fun getAllUser(): Response<ArrayList<User>> = productApiService.getAllUsers()
}