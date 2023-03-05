package algokelvin.app.circlecalculator

import algokelvin.app.circlecalculator.circle.CircleCalculator
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.google.common.truth.Truth.assertThat
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito

/*
Testing ViewModel using mockito
 */

class MainViewModelTest {

    private lateinit var mainViewModel: MainViewModel
    private lateinit var circleCalculator: CircleCalculator

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setUp() {
        circleCalculator = Mockito.mock(CircleCalculator::class.java)
        Mockito.`when`(circleCalculator.calculateArea(2.0)).thenReturn(12.56)
        Mockito.`when`(circleCalculator.calculateCircumference(2.0)).thenReturn(12.56)
        mainViewModel = MainViewModel(circleCalculator)
    }

    @Test
    fun calculateArea_radiusSent_updateLiveData() {
        mainViewModel.getArea(2.0)
        val result = mainViewModel.areaValue.value
        assertThat(result).isEqualTo("12.56")
    }

    @Test
    fun calculateCircumference_radiusSent_updateLiveData() {
        mainViewModel.getCircumference(2.0)
        val result = mainViewModel.circumferenceValue.value
        assertThat(result).isEqualTo("12.56")
    }

}