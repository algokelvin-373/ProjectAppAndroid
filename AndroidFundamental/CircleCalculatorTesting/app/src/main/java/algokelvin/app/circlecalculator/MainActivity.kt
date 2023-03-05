package algokelvin.app.circlecalculator

import algokelvin.app.circlecalculator.circle.CircleCalculatorImpl
import algokelvin.app.circlecalculator.databinding.ActivityMainBinding
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    private val mainViewModelFactory by lazy {
        MainViewModelFactory(CircleCalculatorImpl())
    }
    private val mainViewModel by lazy {
        ViewModelProvider(this, mainViewModelFactory)[MainViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        binding.mainViewModel = mainViewModel
        binding.lifecycleOwner = this

    }
}