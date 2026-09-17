package com.algokelvin.uirecyclerview

import algokelvin.app.recyclerviewbasic.menu
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.algokelvin.uirecyclerview.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.rvMenu.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = MainAdapter(menu())
        }

    }
}