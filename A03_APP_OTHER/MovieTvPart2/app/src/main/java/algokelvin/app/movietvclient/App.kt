package algokelvin.app.movietvclient

import algokelvin.app.movietvclient.presentation.di.Injector
import algokelvin.app.movietvclient.presentation.di.artist.ArtistSubComponent
import algokelvin.app.movietvclient.presentation.di.core.*
import algokelvin.app.movietvclient.presentation.di.movie.MovieSubComponent
import algokelvin.app.movietvclient.presentation.di.tvShow.TvShowSubComponent
import android.app.Application

class App: Application(), Injector {
    private lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()
        appComponent = DaggerAppComponent.builder()
            .appModule(AppModule(applicationContext))
            .netModule(NetModule(BuildConfig.URL))
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
        return  appComponent.artistSubComponent().create()
    }
}