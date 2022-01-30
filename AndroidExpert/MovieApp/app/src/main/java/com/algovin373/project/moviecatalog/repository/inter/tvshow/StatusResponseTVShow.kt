package com.algovin373.project.moviecatalog.repository.inter.tvshow

import com.algovin373.project.moviecatalog.model.DataTVShow

interface StatusResponseTVShow {
    fun onSuccess(list: List<DataTVShow>)
    fun onFailed()
}