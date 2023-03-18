package algokelvin.app.movietvclient.presentation.movie

import algokelvin.app.movietvclient.R
import algokelvin.app.movietvclient.databinding.ActivityMovieBinding
import algokelvin.app.movietvclient.domain.usecase.movie.GetMoviesUseCase
import algokelvin.app.movietvclient.domain.usecase.movie.UpdateMoviesUseCase
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider

class MovieActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMovieBinding

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