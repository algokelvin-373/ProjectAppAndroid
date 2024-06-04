package com.anushka.tmdbclient.data.repository.tvshow.datasource

import com.anushka.tmdbclient.data.model.movie.Movie
import com.anushka.tmdbclient.data.model.tvshow.TvShow

interface TvShowCacheDataSource {
    suspend fun getTvShowsFromCache():List<TvShow>
    suspend fun saveTvShowsToCache(tvShows:List<TvShow>)

}