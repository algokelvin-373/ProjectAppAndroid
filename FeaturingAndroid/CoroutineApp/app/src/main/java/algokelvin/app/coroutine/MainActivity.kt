package algokelvin.app.coroutine

import algokelvin.app.coroutine.databinding.ActivityMainBinding
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnNoCoroutine.setOnClickListener {
            startActivity(Intent(this, NoCoroutineActivity::class.java))
        }
        binding.btnCoroutineBasic.setOnClickListener {
            startActivity(Intent(this, CoroutineBasicActivity::class.java))
        }

    }
}