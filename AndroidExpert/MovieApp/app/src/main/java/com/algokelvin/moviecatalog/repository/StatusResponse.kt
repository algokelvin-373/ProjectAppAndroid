package com.algokelvin.moviecatalog.repository

interface StatusResponse {
    fun<T> onSuccess(data: T?)
    fun onFailed(error: String)
}