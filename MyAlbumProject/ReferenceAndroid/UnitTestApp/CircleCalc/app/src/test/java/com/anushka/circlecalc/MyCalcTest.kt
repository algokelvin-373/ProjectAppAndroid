package com.anushka.circlecalc
import com.google.common.truth.Truth.assertThat
import org.junit.Before
import org.junit.Test

class MyCalcTest{
    private lateinit var myCalc: MyCalc

    @Before
    fun setUp() {
        myCalc = MyCalc()
    }

    @Test
    fun calculateCircumference_radiusGiven_returnsCorrectResult(){
       val result = myCalc.calculateCircumference(2.1)
       assertThat(result).isEqualTo(13.188)

    }

    @Test
    fun calculateCircumference_zeroRadius_returnsCorrectResult(){
        val result = myCalc.calculateCircumference(0.0)
        assertThat(result).isEqualTo(0.0)

    }

    @Test
    fun calculateArea_radiusGiven_returnsCorrectResult(){
        val result = myCalc.calculateArea(2.1)
        assertThat(result).isEqualTo(13.8474)

    }

    @Test
    fun calculateArea_zeroRadius_returnsCorrectResult(){
        val result = myCalc.calculateCircumference(0.0)
        assertThat(result).isEqualTo(0.0)

    }

}














