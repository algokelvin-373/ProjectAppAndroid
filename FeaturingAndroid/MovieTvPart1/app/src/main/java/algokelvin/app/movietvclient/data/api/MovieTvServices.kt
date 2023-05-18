package algokelvin.app.movietvclient.data.api

import algokelvin.app.movietvclient.data.model.artist.ArtistList
import algokelvin.app.movietvclient.data.model.movies.MovieList
import algokelvin.app.movietvclient.data.model.tvshows.TvShowList
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface MovieTvServices {
    @GET("movie/popular")
    suspend fun getPopularMovies(@Query("api_key") api_key: String): Response<MovieList>

    @GET("tv/popular")
    suspend fun getPopularTvShows(@Query("api_key") api_key: String): Response<TvShowList>

    @GET("person/popular")
    suspend fun getPopularArtists(@Query("api_key") api_key: String): Response<ArtistList>
}