package algokelvin.app.circlecalculator.circle

import com.google.common.truth.Truth.assertThat
import org.junit.Before
import org.junit.Test

/*
Testing using class programming
 */

class CircleCalculatorImplTest {
    private lateinit var circleCalculatorImpl: CircleCalculatorImpl

    @Before
    fun setUp() {
        circleCalculatorImpl = CircleCalculatorImpl()
    }

    @Test
    fun calculateArea_radiusGiven_returnCorrectResult() {
        val area = circleCalculatorImpl.calculateArea(2.0)
        assertThat(area).isEqualTo(12.56)
    }

    @Test
    fun calculateCircumference_radiusGiven_returnCorrectResult() {
        val circumference = circleCalculatorImpl.calculateCircumference(2.0)
        assertThat(circumference).isEqualTo(12.56)
    }

}