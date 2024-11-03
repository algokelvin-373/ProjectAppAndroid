package com.algokelvin.movieapp.data.api

import com.algokelvin.movieapp.data.model.cart.Cart
import com.algokelvin.movieapp.data.model.product.Product
import com.algokelvin.movieapp.data.model.user.Login
import com.algokelvin.movieapp.data.model.user.Token
import com.algokelvin.movieapp.data.model.user.User
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface ProductApiService {
    @GET("products")
    suspend fun getProductsList(): Response<ArrayList<Product>>

    @GET("products/{id}")
    suspend fun getProductDetailList(@Path("id") id: String): Response<Product>

    @GET("carts/user/{id}")
    suspend fun getCartByIdUser(@Path("id") id: String): Response<ArrayList<Cart>>

    @POST("auth/login")
    suspend fun login(@Body login: Login): Response<Token>

    @GET("users")
    suspend fun getAllUsers(): Response<ArrayList<User>>
}