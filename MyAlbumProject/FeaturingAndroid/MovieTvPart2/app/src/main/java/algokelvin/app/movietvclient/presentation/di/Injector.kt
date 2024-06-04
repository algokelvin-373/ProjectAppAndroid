package algokelvin.app.movietvclient.presentation.di

import algokelvin.app.movietvclient.presentation.di.artist.ArtistSubComponent
import algokelvin.app.movietvclient.presentation.di.movie.MovieSubComponent
import algokelvin.app.movietvclient.presentation.di.tvShow.TvShowSubComponent

interface Injector {

    fun createMovieSubComponent(): MovieSubComponent
    fun createTvShowSubComponent(): TvShowSubComponent
    fun createArtistSubComponent(): ArtistSubComponent

}