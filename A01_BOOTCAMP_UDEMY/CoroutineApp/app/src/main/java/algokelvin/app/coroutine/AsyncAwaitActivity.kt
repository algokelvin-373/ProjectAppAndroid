package algokelvin.app.coroutine

import algokelvin.app.coroutine.databinding.ActivityAsyncAwaitBinding
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class AsyncAwaitActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAsyncAwaitBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAsyncAwaitBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnCalculateAwait.setOnClickListener {
            MainActivity.resultAsyncAwait = ""
            CoroutineScope(Dispatchers.IO).launch {
                calculateWithAwait()
            }
        }

        binding.btnCalculateAsyncAwait.setOnClickListener {
            MainActivity.resultAsyncAwait = ""
            CoroutineScope(Dispatchers.Main).launch {
                calculateWithAsyncAwait()
            }
        }

    }

    private suspend fun calculateWithAsyncAwait() = coroutineScope {
        Log.i("ALGOKELVIN_LOG", "Calculations started... ")
        MainActivity.resultAsyncAwait += "Calculations started... \n"
        binding.txtResult.text = MainActivity.resultAsyncAwait

        val stock1 = async(IO) {
            getStock1()
        }
        val stock2 = async(IO) {
            getStock2()
        }
        val total = stock1.await() + stock2.await()

        MainActivity.resultAsyncAwait += "Total is $total"
        Log.i("ALGOKELVIN_LOG", "Total is $total")
        binding.txtResult.text = MainActivity.resultAsyncAwait
    }

    private suspend fun calculateWithAwait() {
        Log.i("ALGOKELVIN_LOG", "Calculations started... ")
        MainActivity.resultAsyncAwait += "Calculations started... \n"
        //binding.txtResult.text = MainActivity.resultAsyncAwait

        val stock1 = getStock1()
        val stock2 = getStock2()
        val total = stock1 + stock2
        Log.i("ALGOKELVIN_LOG", "Total is $total")
        MainActivity.resultAsyncAwait += "Total is $total"
        Log.i("ALGOKELVIN_LOG", MainActivity.resultAsyncAwait)
        //binding.txtResult.text = MainActivity.resultAsyncAwait
    }

    private suspend fun getStock1(): Int {
        val stock1 = 55000
        delay(10000)
        MainActivity.resultAsyncAwait += "Stock 1 returned : $stock1 \n"
        //binding.txtResult.text = MainActivity.resultAsyncAwait
        Log.i("ALGOKELVIN_LOG", "Stock 1 returned")
        return stock1
    }

    private suspend fun getStock2(): Int {
        val stock2 = 35000
        delay(8000)
        MainActivity.resultAsyncAwait += "Stock 2 returned : $stock2 \n"
        //binding.txtResult.text = MainActivity.resultAsyncAwait
        Log.i("ALGOKELVIN_LOG", "Stock 2 returned")
        return 35000
    }

}
