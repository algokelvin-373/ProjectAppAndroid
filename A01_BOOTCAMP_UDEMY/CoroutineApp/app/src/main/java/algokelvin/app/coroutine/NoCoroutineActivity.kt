package algokelvin.app.coroutine

import algokelvin.app.coroutine.databinding.ActivityCoroutineBinding
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log

class NoCoroutineActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCoroutineBinding

    private var count = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCoroutineBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnCount.setOnClickListener {
            binding.txtCount.text = getString(R.string.data_count, (++count).toString())
        }

        binding.btnDownloadUserData.setOnClickListener {
            val start = System.currentTimeMillis()
            downloadData()
            val finish = System.currentTimeMillis()
            Log.i("ALGOKELVIN", "Times = ${finish - start}")
        }

    }

    private fun downloadData() {
        for (i in 1 until 1000000) {
            Log.i("ALGOKELVIN", "Downloading user $i in ${Thread.currentThread().name}")
        }
    }

}