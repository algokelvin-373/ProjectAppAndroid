package com.algokelvin.movieapp.presentation.di.core

import com.algokelvin.movieapp.presentation.di.artist.ArtistSubComponent
import com.algokelvin.movieapp.presentation.di.login.LoginSubComponent
import com.algokelvin.movieapp.presentation.di.movie.MovieSubComponent
import com.algokelvin.movieapp.presentation.di.movie.ProductDetailSubComponent
import com.algokelvin.movieapp.presentation.di.tv.TvShowSubComponent
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [
    AppModule::class,
    NetModule::class,
    DatabaseModule::class,
    UseCaseModule::class,
    RepositoryModule::class,
    RemoteDataModule::class,
    LocalDataModule::class,
    CacheDataModule::class
])
interface AppComponent {
    fun movieSubComponent(): MovieSubComponent.Factory
    fun tvShowSubComponent(): TvShowSubComponent.Factory
    fun artistSubComponent(): ArtistSubComponent.Factory
    fun productDetailSubComponent(): ProductDetailSubComponent.Factory
    fun loginSubComponent(): LoginSubComponent.Factory
}