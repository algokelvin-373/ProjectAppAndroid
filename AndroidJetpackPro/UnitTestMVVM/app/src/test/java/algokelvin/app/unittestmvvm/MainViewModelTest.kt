package algokelvin.app.unittestmvvm

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.nhaarman.mockitokotlin2.verify
import junit.framework.TestCase
import org.junit.Assert.assertNotEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class MainViewModelTest: TestCase() {

    private lateinit var viewModel: MainViewModel

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var observer: Observer<String>

    @Before
    public override fun setUp() {
        viewModel = MainViewModel()
    }

    @Test
    fun checkData() {
        val dataSample = "AlgoKelvin"

        val resultData = viewModel.getData(dataSample).value
        assertNotNull(resultData)
        assertEquals(resultData, dataSample)

        viewModel.getData(dataSample).observeForever(observer)
        verify(observer).onChanged(dataSample)

    }

    @Test
    fun checkNotEqualData() {
        val dataSample = "AlgoKelvin"
        val dataOther = "Kelvin"

        val resultData = viewModel.getData(dataOther).value
        assertNotNull(resultData)
        assertNotEquals(resultData, dataSample)

        viewModel.getData(dataOther).observeForever(observer)
        verify(observer).onChanged(dataOther)
    }

}