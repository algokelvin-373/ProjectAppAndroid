package com.algokelvin.movieapp.domain.repository

import com.algokelvin.movieapp.data.model.tv.TvShow

interface TvShowRepository {
    suspend fun getTvShows(): List<TvShow>?
    suspend fun updateTvShows(): List<TvShow>?
}