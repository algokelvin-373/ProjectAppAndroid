package com.algokelvin.moviecatalog.repository.inter.movie

import com.algokelvin.moviecatalog.model.DetailMovie

interface StatusResponseDetailMovie {
    fun onSuccess(data: DetailMovie)
    fun onFailed()
}