package com.algokelvin.moviecatalog.repository.inter.tvshow

import com.algokelvin.moviecatalog.model.entity.DataTVShow

interface StatusResponseTVShow {
    fun onSuccess(list: List<DataTVShow>)
    fun onFailed()
}