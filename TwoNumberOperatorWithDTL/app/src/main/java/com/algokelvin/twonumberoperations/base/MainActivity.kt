package com.algokelvin.twonumberoperations.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.algokelvin.twonumberoperations.databinding.ActivityMainBinding
import com.algokelvin.twonumberoperations.model.DataNumber
import com.algokelvin.twonumberoperations.presenter.MainPresenter

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var mainPresenter: MainPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        mainPresenter = MainPresenter()

        binding.btnPlus.setOnClickListener {
            val dataNumber = DataNumber(binding.edtNumber1.text.toString(), binding.edtNumber2.text.toString())
            val result = mainPresenter.calculatePlusOperator(dataNumber)
            binding.txtResult.text = result.toString()
        }
        binding.btnMinus.setOnClickListener {
            val dataNumber = DataNumber(binding.edtNumber1.text.toString(), binding.edtNumber2.text.toString())
            val result = mainPresenter.calculateMinusOperator(dataNumber)
            binding.txtResult.text = result.toString()
        }
    }
}