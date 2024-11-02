package com.algokelvin.movieapp.presentation.di

import com.algokelvin.movieapp.presentation.di.artist.ArtistSubComponent
import com.algokelvin.movieapp.presentation.di.login.LoginSubComponent
import com.algokelvin.movieapp.presentation.di.movie.MovieSubComponent
import com.algokelvin.movieapp.presentation.di.movie.ProductCategorySubComponent
import com.algokelvin.movieapp.presentation.di.movie.ProductDetailSubComponent
import com.algokelvin.movieapp.presentation.di.tv.TvShowSubComponent

interface Injector {
    fun createMovieSubComponent(): MovieSubComponent
    fun createTvShowSubComponent(): TvShowSubComponent
    fun createArtistSubComponent(): ArtistSubComponent
    fun createProductDetailSubComponent(): ProductDetailSubComponent
    fun createLoginSubComponent(): LoginSubComponent
    fun createProductCategorySubComponent(): ProductCategorySubComponent
}