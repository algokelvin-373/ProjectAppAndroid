package com.algokelvin.movieapp.data.api

import com.algokelvin.movieapp.data.model.Product
import com.algokelvin.movieapp.data.model.artist.ArtistList
import com.algokelvin.movieapp.data.model.ProductList
import com.algokelvin.movieapp.data.model.movie.MovieList
import com.algokelvin.movieapp.data.model.tv.TvShowList
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ProductApiService {
    @GET("movie/popular")
    suspend fun getMovieListPopular(@Query("api_key") apiKey: String): Response<MovieList>

    @GET("tv/popular")
    suspend fun getTvShowListPopular(@Query("api_key") apiKey: String): Response<TvShowList>

    @GET("person/popular")
    suspend fun getArtistListPopular(@Query("api_key") apiKey: String): Response<ArtistList>

    @GET("products")
    suspend fun getProductsList(): Response<ArrayList<Product>>
}