package com.algokelvin.moviecatalog.repository.inter.movie

import com.algokelvin.moviecatalog.model.entity.Keyword

interface StatusResponseKeywordMovie {
    fun onSuccess(dataKeyword: ArrayList<Keyword>)
    fun onFailed()
}