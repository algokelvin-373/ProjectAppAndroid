package algokelvin.app.movietvclient.presentation.di.core

import algokelvin.app.movietvclient.data.api.MovieTvServices
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
class NetModule(private val baseUrl: String) {

    @Singleton
    @Provides
    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(baseUrl)
            .build()
    }

    @Singleton
    @Provides
    fun provideMovieTvService(retrofit: Retrofit): MovieTvServices {
        return retrofit.create(MovieTvServices::class.java)
    }

}