package com.algokelvin.rectangle

import org.junit.Test

import org.junit.Assert.*
import org.junit.Before

class MainViewModelTest {
    private lateinit var mainViewModel: MainViewModel
    var result = 0

    @Before
    fun init() {
        mainViewModel = MainViewModel()
    }

    @Test
    fun calculate() {
        val width = "2"
        val height = "3"
        mainViewModel.calculate(width, height)
        assertEquals(6, mainViewModel.getResults())
    }
}