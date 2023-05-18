package algokelvin.app.unittests.setup

import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class MyCalculationTest {
    private val delta = 1e-15
    private lateinit var myCalculation: MyCalculation

    @Before
    fun setUp() {
        myCalculation = MyCalculation()
    }

    @Test
    fun calculateCircumference_radiusGiven_returnCorrectResult() {
        val result = myCalculation.calculateCircumference(2.1)
        assertEquals(result, 13.188, delta)
    }

    @Test
    fun calculateCircumference_zeroGiven_returnCorrectResult() {
        val result = myCalculation.calculateCircumference(0.0)
        assertEquals(result, 0.0, 0.0)
    }

    @Test
    fun calculateArea_radiusGiven_returnCorrectResult() {
        val result = myCalculation.calculateArea(2.1)
        assertEquals(result, 13.8474, delta)
    }

    @Test
    fun calculateArea_zeroGiven_returnCorrectResult() {
        val result = myCalculation.calculateArea(0.0)
        assertEquals(result, 0.0, 0.0)
    }

}