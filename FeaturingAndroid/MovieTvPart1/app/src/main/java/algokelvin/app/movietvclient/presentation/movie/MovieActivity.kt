package algokelvin.app.movietvclient.presentation.movie

import algokelvin.app.movietvclient.BuildConfig
import algokelvin.app.movietvclient.R
import algokelvin.app.movietvclient.data.api.MovieTvServices
import algokelvin.app.movietvclient.data.api.RetrofitInstance
import algokelvin.app.movietvclient.data.repository.movies.datasource.MovieRemoteDataSource
import algokelvin.app.movietvclient.data.repository.movies.datasourceImpl.MovieLocalDataSourceImpl
import algokelvin.app.movietvclient.data.repository.movies.datasourceImpl.MovieRemoteDataSourceImpl
import algokelvin.app.movietvclient.data.repository.movies.datasourceImpl.MovieRepositoryImpl
import algokelvin.app.movietvclient.databinding.ActivityMovieBinding
import algokelvin.app.movietvclient.domain.usecase.movie.GetMoviesUseCase
import algokelvin.app.movietvclient.domain.usecase.movie.UpdateMoviesUseCase
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider

class MovieActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMovieBinding

    private val movieService by lazy {
        RetrofitInstance.getRetrofitInstance().create(MovieTvServices::class.java)
    }


    private val movieRemoteDataSource = MovieRemoteDataSourceImpl(movieService, BuildConfig.URL)
    private val movieLocalDataSource = MovieLocalDataSourceImpl()

    private val movieRepository = MovieRepositoryImpl(
        movieRemoteDataSource,
        movieLocalDataSource,
    )

    private val factory by lazy {
        MovieViewModelFactory(GetMoviesUseCase(), UpdateMoviesUseCase())
    }

    private val movieViewModel by lazy {
        ViewModelProvider(this, factory)[MovieViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_movie)
        setContentView(R.layout.activity_movie)
    }
}