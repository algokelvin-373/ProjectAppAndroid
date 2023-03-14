package com.anushka.circlecalc

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class CalcViewModelFactory(
    private val calculations: Calculations
) : ViewModelProvider.Factory  {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return CalcViewModel(calculations) as T
    }
}