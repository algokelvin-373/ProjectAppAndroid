package com.algovin373.project.moviecatalog.repository.inter.movie

import com.algovin373.project.moviecatalog.model.DataMovie

interface StatusResponseMovie {
    fun onSuccess(list: List<DataMovie>)
    fun onFailed()
}