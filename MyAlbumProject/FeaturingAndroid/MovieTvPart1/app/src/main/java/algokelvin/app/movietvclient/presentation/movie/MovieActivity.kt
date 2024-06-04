package algokelvin.app.movietvclient.presentation.movie

import algokelvin.app.movietvclient.BuildConfig
import algokelvin.app.movietvclient.R
import algokelvin.app.movietvclient.data.api.MovieTvServices
import algokelvin.app.movietvclient.data.api.RetrofitInstance
import algokelvin.app.movietvclient.data.db.MovieTvDatabase
import algokelvin.app.movietvclient.data.repository.movies.datasourceImpl.MovieCacheDataSourceImpl
import algokelvin.app.movietvclient.data.repository.movies.datasourceImpl.MovieLocalDataSourceImpl
import algokelvin.app.movietvclient.data.repository.movies.datasourceImpl.MovieRemoteDataSourceImpl
import algokelvin.app.movietvclient.data.repository.movies.datasourceImpl.MovieRepositoryImpl
import algokelvin.app.movietvclient.databinding.ActivityMovieBinding
import algokelvin.app.movietvclient.domain.usecase.movie.GetMoviesUseCase
import algokelvin.app.movietvclient.domain.usecase.movie.UpdateMoviesUseCase
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager

class MovieActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMovieBinding
    private lateinit var movieViewModel: MovieViewModel

    private val movieService by lazy {
        RetrofitInstance.getRetrofitInstance().create(MovieTvServices::class.java)
    }

    private val movieRemoteDataSource = MovieRemoteDataSourceImpl(movieService, BuildConfig.API_KEY)
    private val movieCacheDataSource = MovieCacheDataSourceImpl()

    private lateinit var adapter: MovieAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_movie)

        // Injection not using Dagger
        val movieDao = MovieTvDatabase.getInstance(applicationContext).movieDao()
        val movieLocalDataSource = MovieLocalDataSourceImpl(movieDao)
        val movieRepository = MovieRepositoryImpl(
            movieRemoteDataSource,
            movieLocalDataSource,
            movieCacheDataSource
        )
        val factory = MovieViewModelFactory(
            GetMoviesUseCase(movieRepository),
            UpdateMoviesUseCase(movieRepository)
        )
        movieViewModel = ViewModelProvider(this, factory)[MovieViewModel::class.java]

        movieViewModel.getMovies().observe(this) {
            Log.i("AlgoKelvin", it.toString())
        }

        showMovies()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.update, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId) {
            R.id.action_update -> {
                updateMovies()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun showMovies() {
        binding.movieProgressBar.visibility = View.VISIBLE
        binding.movieRecyclerView.layoutManager = LinearLayoutManager(this)
        adapter = MovieAdapter()
        binding.movieRecyclerView.adapter = adapter

        movieViewModel.getMovies().observe(this) {
            if (it != null) {
                adapter.setList(it)
                adapter.notifyDataSetChanged()
            } else {
                Toast.makeText(this, "No data available", Toast.LENGTH_LONG).show()
            }
            binding.movieProgressBar.visibility = View.GONE
        }
    }

    private fun updateMovies() {
        binding.movieProgressBar.visibility = View.VISIBLE

        movieViewModel.updateMovies().observe(this) {
            if (it != null) {
                adapter.setList(it)
                adapter.notifyDataSetChanged()
            }
            binding.movieProgressBar.visibility = View.GONE
        }
    }

}