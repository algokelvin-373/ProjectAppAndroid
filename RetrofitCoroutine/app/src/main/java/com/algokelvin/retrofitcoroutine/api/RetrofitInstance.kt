package com.algokelvin.retrofitcoroutine.api

import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class RetrofitInstance {
    companion object {
        private const val BASE_URL = "https://jsonplaceholder.typicode.com"
        private val interceptor = HttpLoggingInterceptor().apply {
            this.level = HttpLoggingInterceptor.Level.BASIC
        }

        // Optional to using this: To show response on logging
        private val client = OkHttpClient.Builder().apply {
            this.addInterceptor(interceptor)
                .connectTimeout(1, TimeUnit.SECONDS)
                .readTimeout(1, TimeUnit.SECONDS)
                .writeTimeout(1, TimeUnit.SECONDS)
        }.build()

        fun getRetrofitInstance(): Retrofit {
            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
                .build()
        }
    }
}