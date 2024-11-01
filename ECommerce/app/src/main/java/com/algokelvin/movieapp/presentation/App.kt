package com.algokelvin.movieapp.presentation

import android.app.Application
import com.algokelvin.movieapp.BuildConfig
import com.algokelvin.movieapp.presentation.di.Injector
import com.algokelvin.movieapp.presentation.di.artist.ArtistSubComponent
import com.algokelvin.movieapp.presentation.di.core.AppComponent
import com.algokelvin.movieapp.presentation.di.core.AppModule
import com.algokelvin.movieapp.presentation.di.core.DaggerAppComponent
import com.algokelvin.movieapp.presentation.di.core.NetModule
import com.algokelvin.movieapp.presentation.di.core.RemoteDataModule
import com.algokelvin.movieapp.presentation.di.movie.MovieSubComponent
import com.algokelvin.movieapp.presentation.di.movie.ProductDetailSubComponent
import com.algokelvin.movieapp.presentation.di.tv.TvShowSubComponent

class App: Application(), Injector {
    private lateinit var appComponent:AppComponent

    override fun onCreate() {
        super.onCreate()
        appComponent = DaggerAppComponent.builder()
            .appModule(AppModule(applicationContext))
            .netModule(NetModule(BuildConfig.BASE_URL))
            .remoteDataModule(RemoteDataModule(BuildConfig.API_KEY))
            .build()
    }

    override fun createMovieSubComponent(): MovieSubComponent {
        return appComponent.movieSubComponent().create()
    }

    override fun createTvShowSubComponent(): TvShowSubComponent {
        return appComponent.tvShowSubComponent().create()
    }

    override fun createArtistSubComponent(): ArtistSubComponent {
        return appComponent.artistSubComponent().create()
    }

    override fun createProductDetailSubComponent(): ProductDetailSubComponent {
        return appComponent.productDetailSubComponent().create()
    }
}