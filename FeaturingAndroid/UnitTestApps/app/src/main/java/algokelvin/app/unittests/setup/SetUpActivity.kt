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

        }

    }

    private fun calculate() {

    }

}