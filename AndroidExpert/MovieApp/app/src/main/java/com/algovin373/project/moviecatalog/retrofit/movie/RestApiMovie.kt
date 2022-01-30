package com.algovin373.project.moviecatalog.retrofit.movie

import com.algovin373.project.moviecatalog.BuildConfig
import com.algovin373.project.moviecatalog.model.Cast
import com.algovin373.project.moviecatalog.model.DetailMovie
import com.algovin373.project.moviecatalog.model.KeywordMovie
import com.algovin373.project.moviecatalog.model.Movie
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Path

interface RestApiMovie {

    @GET("/3/movie/now_playing?api_key=${BuildConfig.API_KEY}&language=en-US ")
    fun getDataMovieNowPlaying() : Observable<Movie>

    @GET("/3/movie/popular?api_key=${BuildConfig.API_KEY}&language=en-US ")
    fun getDataMoviePopular() : Observable<Movie>

    @GET("/3/movie/top_rated?api_key=${BuildConfig.API_KEY}&language=en-US ")
    fun getDataMovieTopRated() : Observable<Movie>

    @GET("/3/movie/upcoming?api_key=${BuildConfig.API_KEY}&language=en-US ")
    fun getDataMovieUpComing() : Observable<Movie>

    @GET("/3/movie/{movie_id}?api_key=${BuildConfig.API_KEY}&language=en-US")
    fun getDetailMovie(@Path("movie_id") id : String) : Observable<DetailMovie>

    @GET("/3/movie/{movie_id}/keywords?api_key=${BuildConfig.API_KEY}")
    fun getKeywordMovie(@Path("movie_id") id : String) : Observable<KeywordMovie>

    @GET("/3/movie/{movie_id}/credits?api_key=${BuildConfig.API_KEY}")
    fun getCastMovie(@Path("movie_id") id : String) : Observable<Cast>

    @GET("/3/movie/{movie_id}/similar?api_key=${BuildConfig.API_KEY}&language=en-US")
    fun getSimilarMovie(@Path("movie_id") id : String) : Observable<Movie>

    @GET("/3/movie/{movie_id}/recommendations?api_key=${BuildConfig.API_KEY}&language=en-US")
    fun getRecommendtionMovie(@Path("movie_id") id : String) : Observable<Movie>
}