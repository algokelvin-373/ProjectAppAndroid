package algokelvin.app.movietvclient.presentation.di.core

import algokelvin.app.movietvclient.presentation.di.artist.ArtistSubComponent
import algokelvin.app.movietvclient.presentation.di.movie.MovieSubComponent
import algokelvin.app.movietvclient.presentation.di.tvShow.TvShowSubComponent
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [
    AppModule::class,
    CacheDataModule::class,
    DatabaseModule::class,
    LocalDataModule::class,
    NetModule::class,
    RemoteDataModule::class,
    RepositoryModule::class,
    UseCaseModule::class
])
interface AppComponent {

    fun movieSubComponent(): MovieSubComponent.Factory
    fun tvShowSubComponent(): TvShowSubComponent.Factory
    fun artistSubComponent(): ArtistSubComponent.Factory

}