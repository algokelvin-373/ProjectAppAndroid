package com.algokelvin.shoppingyuk.data.api

data class ResponseResults<T>(val data: T?, val errorMessage: String? = null)
