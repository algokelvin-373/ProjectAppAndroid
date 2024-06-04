package com.anushka.tmdbclient.data.repository.tvshow.datasource

import com.anushka.tmdbclient.data.model.movie.MovieList
import com.anushka.tmdbclient.data.model.tvshow.TvShowList
import retrofit2.Response

interface TvShowRemoteDatasource {
   suspend fun getTvShows(): Response<TvShowList>
}