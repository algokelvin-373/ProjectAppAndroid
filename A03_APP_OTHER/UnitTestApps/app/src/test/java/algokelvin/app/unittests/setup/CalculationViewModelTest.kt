package algokelvin.app.unittests.setup

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito

class CalculationViewModelTest {
    private val delta = 1e-15
    private lateinit var calculationViewModel: CalculationViewModel
    private lateinit var calculations: Calculations

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setUp() {
        calculations = Mockito.mock(Calculations::class.java)
        Mockito.`when`(calculations.calculateArea(2.1)).thenReturn(13.8474)
        Mockito.`when`(calculations.calculateCircumference(1.0)).thenReturn(6.28)
        calculationViewModel = CalculationViewModel(calculations)
    }

    @Test
    fun calculateArea_radiusSent_updateLiveData() {
        calculations.calculateArea(2.1)
        val result = calculationViewModel.areaValue.value
        result?.let {
            assertEquals(it.toDouble(), 13.8474, delta)
        }
    }

    @Test
    fun calculateCircumference_radiusSent_updateLiveData() {
        calculations.calculateCircumference(1.0)
        val result = calculationViewModel.circumferenceValue.value
        result?.let {
            assertEquals(it.toDouble(), 6.28, delta)
        }
    }



}