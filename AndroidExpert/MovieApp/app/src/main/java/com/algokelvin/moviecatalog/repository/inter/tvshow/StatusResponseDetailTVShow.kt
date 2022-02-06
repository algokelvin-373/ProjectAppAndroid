package com.algokelvin.moviecatalog.repository.inter.tvshow

import com.algokelvin.moviecatalog.model.entity.DetailTVShow

interface StatusResponseDetailTVShow {
    fun onSuccess(data: DetailTVShow)
    fun onFailed()
}