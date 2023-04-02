package algokelvin.app.room.maintwo

import algokelvin.app.room.R
import algokelvin.app.room.databinding.ActivityMainBinding
import algokelvin.app.room.db.SubscriberDB
import algokelvin.app.room.db.SubscriberRepository
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider

class MainTwoActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    private val dao by lazy {
        SubscriberDB.getDb(application).subscriberDao()
    }

    private val repository by lazy {
        SubscriberRepository(dao)
    }

    private val factory by lazy {
        MainTwoViewModelFactory(repository)
    }

    private val mainTwoViewModel by lazy {
        ViewModelProvider(this, factory)[MainTwoViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.mainTwoViewModel = mainTwoViewModel
        binding.lifecycleOwner = this
        showSubscribers()
    }

    private fun showSubscribers() {
        mainTwoViewModel.subscribers.observe(this) {
            Log.i("AlgoKelvin", it.toString())
        }
    }

}