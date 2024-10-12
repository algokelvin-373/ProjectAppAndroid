package algokelvin.app.unittests.setup

import algokelvin.app.unittests.databinding.ActivitySetUpBinding
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider

class SetUpActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySetUpBinding
    private val viewModelFactory by lazy {
        CalculationViewModelFactory(MyCalculation())
    }
    private val calculationViewModel by lazy {
        ViewModelProvider(this, viewModelFactory)[CalculationViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySetUpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.calculateButton.setOnClickListener {
            val radius = binding.radiusEditText.text.toString().toDouble()
            calculate(radius)
        }

    }

    private fun calculate(radius: Double) {
        calculationViewModel.calculateCircle(radius)
        calculationViewModel.circumferenceValue.observe(this) {
            binding.circumferenceTextView.text = it
        }
        calculationViewModel.areaValue.observe(this) {
            binding.areaTextView.text = it
        }
    }

}