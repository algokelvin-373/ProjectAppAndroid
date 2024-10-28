package algokelvin.app.coroutine

import algokelvin.app.coroutine.databinding.ActivityMainBinding
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    companion object {
        var resultAsyncAwait = ""
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        CoroutineScope(Dispatchers.IO).launch {
            Log.i("ALGOKELVIN", "Hello from ${Thread.currentThread().name}")
        }

        CoroutineScope(Dispatchers.Main).launch {
            Log.i("ALGOKELVIN", "Hello from ${Thread.currentThread().name}")
        }

        binding.btnNoCoroutine.setOnClickListener {
            startActivity(Intent(this, NoCoroutineActivity::class.java))
        }
        binding.btnCoroutineBasic.setOnClickListener {
            startActivity(Intent(this, CoroutineBasicActivity::class.java))
        }
        binding.btnDispatchersMain.setOnClickListener {
            startActivity(Intent(this, DispatcherMainActivity::class.java))
        }
        binding.btnAsyncAwait.setOnClickListener {
            startActivity(Intent(this, AsyncAwaitActivity::class.java))
        }

    }
}