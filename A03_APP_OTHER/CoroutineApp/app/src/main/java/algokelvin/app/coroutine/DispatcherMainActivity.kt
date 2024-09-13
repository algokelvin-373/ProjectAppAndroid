package algokelvin.app.coroutine

import algokelvin.app.coroutine.databinding.ActivityCoroutineBinding
import algokelvin.app.coroutine.databinding.ActivityDispatcherMainBinding
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DispatcherMainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDispatcherMainBinding

    private var count = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDispatcherMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnDownloadUserData.setOnClickListener {
            val start = System.currentTimeMillis()
            CoroutineScope(Dispatchers.IO).launch {
                downloadData()
            }
            val finish = System.currentTimeMillis()
            Log.i("ALGOKELVIN", "Times = ${finish - start}")
        }

    }

    private suspend fun downloadData() {
        for (i in 1 until 1000000) {
            withContext(Dispatchers.Main) {
                binding.txtCount.text = ("Downloading user $i in ${Thread.currentThread().name}")
            }
        }
    }
}