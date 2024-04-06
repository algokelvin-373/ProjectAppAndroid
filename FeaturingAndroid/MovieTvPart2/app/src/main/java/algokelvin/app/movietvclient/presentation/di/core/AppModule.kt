package algokelvin.app.movietvclient.presentation.di.core

import algokelvin.app.movietvclient.presentation.di.artist.ArtistSubComponent
import algokelvin.app.movietvclient.presentation.di.movie.MovieSubComponent
import algokelvin.app.movietvclient.presentation.di.tvShow.TvShowSubComponent
import android.content.Context
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module(subcomponents = [
    MovieSubComponent::class,
    TvShowSubComponent::class,
    ArtistSubComponent::class
])
class AppModule(private val context: Context) {

    @Singleton
    @Provides
    fun provideApplicationContext(): Context {
        return context.applicationContext
    }

}