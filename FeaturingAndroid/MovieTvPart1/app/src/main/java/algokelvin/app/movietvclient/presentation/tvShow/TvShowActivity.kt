package algokelvin.app.movietvclient.presentation.tvShow

import algokelvin.app.movietvclient.BuildConfig
import algokelvin.app.movietvclient.R
import algokelvin.app.movietvclient.data.api.MovieTvServices
import algokelvin.app.movietvclient.data.api.RetrofitInstance
import algokelvin.app.movietvclient.data.db.MovieTvDatabase
import algokelvin.app.movietvclient.data.repository.tvshows.datasourceImpl.TvShowRepositoryImpl
import algokelvin.app.movietvclient.data.repository.tvshows.datasourceImpl.TvShowCacheDataSourceImpl
import algokelvin.app.movietvclient.data.repository.tvshows.datasourceImpl.TvShowLocalDataSourceImpl
import algokelvin.app.movietvclient.data.repository.tvshows.datasourceImpl.TvShowRemoteDataSourceImpl
import algokelvin.app.movietvclient.databinding.ActivityTvShowBinding
import algokelvin.app.movietvclient.domain.usecase.tvShow.GetTvShowsUseCase
import algokelvin.app.movietvclient.domain.usecase.tvShow.UpdateTvShowsUseCase
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager

class TvShowActivity : AppCompatActivity() {
    private lateinit var binding: ActivityTvShowBinding
    private lateinit var tvShowViewModel: TvShowViewModel
    private lateinit var adapter: TvShowAdapter

    private val tvShowService by lazy {
        RetrofitInstance.getRetrofitInstance().create(MovieTvServices::class.java)
    }

    private val tvShowRemoteDataSource = TvShowRemoteDataSourceImpl(tvShowService, BuildConfig.API_KEY)
    private val tvShowCacheDataSource = TvShowCacheDataSourceImpl()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_tv_show)

        val tvShowDao = MovieTvDatabase.getInstance(this).tvShowDao()
        val tvShowLocalDataSource = TvShowLocalDataSourceImpl(tvShowDao)
        val tvShowRepository = TvShowRepositoryImpl(
            tvShowRemoteDataSource,
            tvShowLocalDataSource,
            tvShowCacheDataSource
        )
        val factory = TvShowViewModelFactory(
            GetTvShowsUseCase(tvShowRepository),
            UpdateTvShowsUseCase(tvShowRepository)
        )
        tvShowViewModel = ViewModelProvider(this, factory)[TvShowViewModel::class.java]

        showTvShows()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.update, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId) {
            R.id.action_update -> {
                updateTvShows()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun showTvShows() {
        binding.tvShowProgressBar.visibility = View.VISIBLE
        binding.tvShowRecyclerView.layoutManager = LinearLayoutManager(this)
        adapter = TvShowAdapter()
        binding.tvShowRecyclerView.adapter = adapter

        tvShowViewModel.getTvShow().observe(this) {
            if (it != null) {
                adapter.setList(it)
                adapter.notifyDataSetChanged()
            } else {
                Toast.makeText(this, "No data available", Toast.LENGTH_LONG).show()
            }
            binding.tvShowProgressBar.visibility = View.GONE
        }
    }

    private fun updateTvShows() {
        binding.tvShowRecyclerView.visibility = View.VISIBLE

        tvShowViewModel.updateTvShow().observe(this) {
            if (it != null) {
                adapter.setList(it)
                adapter.notifyDataSetChanged()
            }
            binding.tvShowProgressBar.visibility = View.GONE
        }
    }
}