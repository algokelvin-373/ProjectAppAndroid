package com.anushka.circlecalc

class MyCalc : Calculations {

    private val pi = 3.14

    override fun calculateCircumference(redius: Double): Double {
        return 2 * pi * redius
    }

    override fun calculateArea(redius: Double): Double {
        return pi * redius * redius
    }
}