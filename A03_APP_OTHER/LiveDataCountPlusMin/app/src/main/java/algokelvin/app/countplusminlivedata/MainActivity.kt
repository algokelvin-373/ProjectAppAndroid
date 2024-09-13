package algokelvin.app.countplusminlivedata

import algokelvin.app.countplusminlivedata.databinding.ActivityMainBinding
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import java.util.Random

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        main()
    }

    private fun main() {
        val controller = MainController(Random().nextInt(100))

        controller.setResult().observe(this) {
            binding.txtResult.text = getString(R.string.result, it.toString())
        }

        binding.btnPlus.setOnClickListener {
            val data = binding.edtNumber.text.toString().toInt()
            controller.setPlus(data).observe(this) {
                binding.txtResult.text = getString(R.string.result, it.toString())
            }
        }

        binding.btnMin.setOnClickListener {
            val data = binding.edtNumber.text.toString().toInt()
            controller.setMin(data).observe(this) {
                binding.txtResult.text = getString(R.string.result, it.toString())
            }
        }
    }

}