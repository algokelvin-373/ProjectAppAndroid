package algokelvin.app.movietvclient

import algokelvin.app.movietvclient.databinding.ActivityHomeBinding
import algokelvin.app.movietvclient.presentation.artist.ArtistActivity
import algokelvin.app.movietvclient.presentation.movie.MovieActivity
import algokelvin.app.movietvclient.presentation.tvShow.TvShowActivity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil

class HomeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_home)

        binding.movieButton.setOnClickListener {
            val intentToMovie = Intent(this, MovieActivity::class.java)
            startActivity(intentToMovie)
        }
        binding.tvButton.setOnClickListener {
            val intentToTvShow = Intent(this, TvShowActivity::class.java)
            startActivity(intentToTvShow)
        }
        binding.artistsButton.setOnClickListener {
            val intentToArtist = Intent(this, ArtistActivity::class.java)
            startActivity(intentToArtist)
        }

    }
}