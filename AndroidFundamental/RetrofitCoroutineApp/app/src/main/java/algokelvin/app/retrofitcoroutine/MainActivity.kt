package algokelvin.app.retrofitcoroutine

import algokelvin.app.retrofitcoroutine.api.AlbumsService
import algokelvin.app.retrofitcoroutine.api.RetrofitInstance
import algokelvin.app.retrofitcoroutine.databinding.ActivityMainBinding
import algokelvin.app.retrofitcoroutine.model.Albums
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
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
            val albums = retrofitService.getAlbums()
            emit(albums)
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

    }
}