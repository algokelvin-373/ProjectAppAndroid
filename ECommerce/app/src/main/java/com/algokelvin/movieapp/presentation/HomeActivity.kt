package com.algokelvin.movieapp.presentation

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.algokelvin.movieapp.R
import com.algokelvin.movieapp.databinding.ActivityHomeBinding
import com.algokelvin.movieapp.presentation.artist.ArtistActivity
import com.algokelvin.movieapp.presentation.login.LoginActivity
import com.algokelvin.movieapp.presentation.product.ProductActivity
import com.algokelvin.movieapp.presentation.tv.TvShowActivity

class HomeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_home)

        binding.productButton.setOnClickListener {
            val intentToMovie = Intent(this, ProductActivity::class.java)
            startActivity(intentToMovie)
        }

        binding.loginButton.setOnClickListener {
            val intentToTvShow = Intent(this, LoginActivity::class.java)
            startActivity(intentToTvShow)
        }

        binding.artistsButton.setOnClickListener {
            val intentToArtist = Intent(this, ArtistActivity::class.java)
            startActivity(intentToArtist)
        }
    }
}