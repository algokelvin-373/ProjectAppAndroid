package com.algokelvin.moviecatalog.repository.inter

import com.algokelvin.moviecatalog.model.entity.DataCast

interface StatusResponseDataCast {
    fun onSuccess(data: List<DataCast>)
    fun onFailed()
}