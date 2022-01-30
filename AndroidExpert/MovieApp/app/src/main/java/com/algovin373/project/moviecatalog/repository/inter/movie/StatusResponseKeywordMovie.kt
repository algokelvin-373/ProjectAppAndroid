package com.algovin373.project.moviecatalog.repository.inter.movie

import com.algovin373.project.moviecatalog.model.Keyword

interface StatusResponseKeywordMovie {
    fun onSuccess(dataKeyword: ArrayList<Keyword>)
    fun onFailed()
}