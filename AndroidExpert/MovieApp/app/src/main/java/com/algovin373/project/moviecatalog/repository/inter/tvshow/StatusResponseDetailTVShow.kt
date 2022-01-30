package com.algovin373.project.moviecatalog.repository.inter.tvshow

import com.algovin373.project.moviecatalog.model.DetailTVShow

interface StatusResponseDetailTVShow {
    fun onSuccess(data: DetailTVShow)
    fun onFailed()
}