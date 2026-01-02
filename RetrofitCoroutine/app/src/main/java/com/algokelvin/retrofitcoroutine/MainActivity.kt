package com.algokelvin.retrofitcoroutine

import com.algokelvin.retrofitcoroutine.api.AlbumsService
import com.algokelvin.retrofitcoroutine.api.RetrofitInstance
import com.algokelvin.retrofitcoroutine.model.Albums
import com.algokelvin.retrofitcoroutine.model.AlbumsItem
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.algokelvin.retrofitcoroutine.databinding.ActivityMainBinding
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    private val retrofitService by lazy {
        RetrofitInstance.getRetrofitInstance().create(AlbumsService::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // LiveData using Coroutines
        val response: LiveData<Response<Albums>> = liveData {
            val albums = retrofitService.getAlbumsByUserId(3)
            emit(albums)
        }
        val responseAlbum: LiveData<Response<AlbumsItem>> = liveData {
            val albums = retrofitService.getAlbumById(3)
            emit(albums)
        }
        val responseUpload: LiveData<Response<AlbumsItem>> = liveData {
            val albumsItem = AlbumsItem(100, "My Title: Calvin", 3)
            val upload = retrofitService.addAlbum(albumsItem)
            emit(upload)
        }

        responseAlbum.observe(this) {
            val albums = it.body()?.title
            Toast.makeText(this, "Album $albums", Toast.LENGTH_LONG).show()
        }

        response.observe(this) {
            val listAlbums = it.body()?.listIterator()
            var data = ""
            if (listAlbums != null) {
                while (listAlbums.hasNext()) {
                    val album = listAlbums.next()
                    data += "${album.id} - ${album.title} \n"
                }
            }
            binding.txtName.text = data
        }

        responseUpload.observe(this) {
            val albums = it.body()
            binding.txtName.text = "${albums?.id}. ${albums?.title}\nId User: ${albums?.userId}"
            Toast.makeText(this, "Success Upload", Toast.LENGTH_LONG).show()
        }

    }
}