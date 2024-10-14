package com.algokelvin.twonumberoperations.presenter

import com.algokelvin.twonumberoperations.model.DataNumber

class MainPresenter {
    fun calculatePlusOperator(dataNumber: DataNumber): Double {
        var num1 = 0.0
        if (dataNumber.number1.isNotEmpty())
            num1 = dataNumber.number1.toDouble()
        var num2 = 0.0
        if (dataNumber.number2.isNotEmpty())
            num2 = dataNumber.number2.toDouble()
        return num1 + num2
    }
    fun calculateMinusOperator(dataNumber: DataNumber): Double {
        var num1 = 0.0
        if (dataNumber.number1.isNotEmpty())
            num1 = dataNumber.number1.toDouble()
        var num2 = 0.0
        if (dataNumber.number2.isNotEmpty())
            num2 = dataNumber.number2.toDouble()
        return num1 - num2
    }
}