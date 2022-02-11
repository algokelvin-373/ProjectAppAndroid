package com.algokelvin.moviecatalog.repository.inter.movie

import com.algokelvin.moviecatalog.model.entity.DataMovie

interface StatusResponseMovie {
    fun onSuccess(list: List<DataMovie>)
    fun onFailed()
}