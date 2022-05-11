package algokelvin.app.unittestmvvm

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.nhaarman.mockitokotlin2.verify
import junit.framework.TestCase
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
    fun createData() {
        val dataSample = "AlgoKelvin"

        val resultData = viewModel.getData(dataSample).value
        assertNotNull(resultData)

        viewModel.getData(dataSample).observeForever(observer)
        verify(observer).onChanged(dataSample)

    }

}