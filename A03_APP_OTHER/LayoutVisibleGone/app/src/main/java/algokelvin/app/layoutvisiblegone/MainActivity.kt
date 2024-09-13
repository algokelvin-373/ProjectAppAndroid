package algokelvin.app.layoutvisiblegone

import algokelvin.app.layoutvisiblegone.databinding.ActivityMainBinding
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private var statusRowOne = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnLayoutOneYes.setOnClickListener {
            statusRowOne = true
            if (statusRowOne) {
                binding.layoutRowOne.visibility = View.GONE
                binding.layoutRowTwo.visibility = View.VISIBLE
            }
        }
        binding.btnLayoutOneNo.setOnClickListener {
            statusRowOne = true
            if (statusRowOne) {
                binding.layoutRowOne.visibility = View.GONE
                binding.layoutRowTwo.visibility = View.VISIBLE
            }
        }

    }
}