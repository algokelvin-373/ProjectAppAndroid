package com.algokelvin.movieapp.presentation.di.core

import android.content.Context
import com.algokelvin.movieapp.presentation.di.artist.ArtistSubComponent
import com.algokelvin.movieapp.presentation.di.movie.MovieSubComponent
import com.algokelvin.movieapp.presentation.di.movie.ProductDetailSubComponent
import com.algokelvin.movieapp.presentation.di.tv.TvShowSubComponent
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module(subcomponents = [
    MovieSubComponent::class,
    TvShowSubComponent::class,
    ArtistSubComponent::class,
    ProductDetailSubComponent::class,
])
class AppModule(private val context: Context) {
    @Singleton
    @Provides
    fun provideApplicationContext(): Context {
        return context.applicationContext
    }
}