package com.algovin373.project.moviecatalog.repository.inter

import com.algovin373.project.moviecatalog.model.DataCast

interface StatusResponseDataCast {
    fun onSuccess(data: List<DataCast>)
    fun onFailed()
}