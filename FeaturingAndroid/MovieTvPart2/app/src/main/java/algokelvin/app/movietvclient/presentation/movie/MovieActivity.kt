package algokelvin.app.movietvclient.presentation.movie

import algokelvin.app.movietvclient.R
import algokelvin.app.movietvclient.databinding.ActivityMovieBinding
import algokelvin.app.movietvclient.presentation.di.Injector
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
import javax.inject.Inject

class MovieActivity : AppCompatActivity() {

    @Inject
    lateinit var factory: MovieViewModelFactory

    private lateinit var binding: ActivityMovieBinding
    private lateinit var movieViewModel: MovieViewModel
    private lateinit var adapter: MovieAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_movie)
        (application as Injector).createMovieSubComponent()
            .inject(this)

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