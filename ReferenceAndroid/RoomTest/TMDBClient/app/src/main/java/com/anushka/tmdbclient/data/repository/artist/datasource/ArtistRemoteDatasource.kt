package com.anushka.tmdbclient.data.repository.artist.datasource

import com.anushka.tmdbclient.data.model.artist.ArtistList
import com.anushka.tmdbclient.data.model.movie.MovieList
import retrofit2.Response

interface ArtistRemoteDatasource {
   suspend fun getArtists(): Response<ArtistList>
}