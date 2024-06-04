package algokelvin.app.movietvclient.presentation.artist

import algokelvin.app.movietvclient.R
import algokelvin.app.movietvclient.databinding.ActivityArtistBinding
import algokelvin.app.movietvclient.presentation.di.Injector
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
import javax.inject.Inject

class ArtistActivity : AppCompatActivity() {

    @Inject
    lateinit var factory: ArtistViewModelFactory

    private lateinit var binding: ActivityArtistBinding
    private lateinit var artistViewModel: ArtistViewModel
    private lateinit var adapter: ArtistAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_artist)
        (application as Injector).createArtistSubComponent()
            .inject(this)

        artistViewModel = ViewModelProvider(this, factory)[ArtistViewModel::class.java]

        showArtist()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.update, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId) {
            R.id.action_update -> {
                updateArtist()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun showArtist() {
        binding.artistProgressBar.visibility = View.VISIBLE
        binding.artistRecyclerView.layoutManager = LinearLayoutManager(this)
        adapter = ArtistAdapter()
        binding.artistRecyclerView.adapter = adapter

        artistViewModel.getArtists().observe(this) {
            if (it != null) {
                adapter.setList(it)
                adapter.notifyDataSetChanged()
            } else {
                Toast.makeText(this, "No data available", Toast.LENGTH_LONG).show()
            }
            binding.artistRecyclerView.visibility = View.GONE
        }
    }

    private fun updateArtist() {
        binding.artistRecyclerView.visibility = View.VISIBLE

        artistViewModel.updateArtists().observe(this) {
            if (it != null) {
                adapter.setList(it)
                adapter.notifyDataSetChanged()
            }
            binding.artistProgressBar.visibility = View.GONE
        }
    }
}