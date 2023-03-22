package algokelvin.app.room.maintwo

import algokelvin.app.room.DataAdapter
import algokelvin.app.room.R
import algokelvin.app.room.databinding.ActivityMainBinding
import algokelvin.app.room.db.Subscriber
import algokelvin.app.room.db.SubscriberDB
import algokelvin.app.room.db.SubscriberRepository
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager

class MainTwoActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var mainTwoViewModel: MainTwoViewModel
    private lateinit var adapter: DataAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        val dao = SubscriberDB.getDb(application).subscriberDao()
        val repository = SubscriberRepository(dao)
        val factory = MainTwoViewModelFactory(repository)
        mainTwoViewModel = ViewModelProvider(this, factory)[MainTwoViewModel::class.java]

        binding.mainTwoViewModel = mainTwoViewModel
        binding.lifecycleOwner = this

        showSubscribers()

        mainTwoViewModel.message.observe(this) {
            it.getContentIfNotHandled()?.let { msg ->
                Toast.makeText(this, msg, Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun showSubscribers() {
        binding.rvSubscriber.layoutManager = LinearLayoutManager(this)
        adapter = DataAdapter { showToastActionClicked(it) }
        binding.rvSubscriber.adapter = adapter
        mainTwoViewModel.subscribers.observe(this) {
            adapter.setList(it)
            adapter.notifyDataSetChanged()
        }
    }

    private fun showToastActionClicked(subscriber: Subscriber) {
        Toast.makeText(
            this,
            "The selected: ${subscriber.name} - ${subscriber.email}",
            Toast.LENGTH_LONG
        ).show()
        mainTwoViewModel.initUpdateOrDelete(subscriber)
    }

}