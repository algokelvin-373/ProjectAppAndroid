package com.algovin373.project.moviecatalog.repository.inter.movie

import com.algovin373.project.moviecatalog.model.DetailMovie

interface StatusResponseDetailMovie {
    fun onSuccess(data: DetailMovie)
    fun onFailed()
}