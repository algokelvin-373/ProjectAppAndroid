package com.algokelvin.moviecatalog.repository.inter.movie

import com.algokelvin.moviecatalog.model.Keyword

interface StatusResponseKeywordMovie {
    fun onSuccess(dataKeyword: ArrayList<Keyword>)
    fun onFailed()
}