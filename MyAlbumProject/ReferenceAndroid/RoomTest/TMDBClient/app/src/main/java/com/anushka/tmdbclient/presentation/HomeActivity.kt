package com.anushka.tmdbclient.presentation

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.anushka.tmdbclient.R
import com.anushka.tmdbclient.databinding.ActivityHomeBinding
import com.anushka.tmdbclient.presentation.artist.ArtistActivity
import com.anushka.tmdbclient.presentation.movie.MovieActivity
import com.anushka.tmdbclient.presentation.tv.TvShowActivity

class HomeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHomeBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_home)
        binding.movieButton.setOnClickListener {
            val intent = Intent(this, MovieActivity::class.java)
            startActivity(intent)
        }

        binding.tvButton.setOnClickListener {
            val intent = Intent(this, TvShowActivity::class.java)
            startActivity(intent)
        }

        binding.artistsButton.setOnClickListener {
            val intent = Intent(this, ArtistActivity::class.java)
            startActivity(intent)
        }
    }
}