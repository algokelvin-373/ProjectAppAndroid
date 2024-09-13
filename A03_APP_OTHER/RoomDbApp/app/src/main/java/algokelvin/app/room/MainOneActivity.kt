package algokelvin.app.room

import algokelvin.app.room.databinding.ActivityMainBinding
import algokelvin.app.room.db.Subscriber
import algokelvin.app.room.db.SubscriberDB
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager

class MainOneActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    private val db by lazy { SubscriberDB.getDb(this) }
    private val database by lazy { db.subscriberDao() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        showSubscriber()
        binding.btnAdd.setOnClickListener {
            val name = binding.edtName.text.toString()
            val email = binding.edtEmail.text.toString()
            insertSubscriber(name, email)
        }
    }

    private fun insertSubscriber(name: String, email: String) {
        val subscriber = Subscriber(name = name, email = email)
        database.insert(subscriber)
        showSubscriber()
    }

    private fun showSubscriber() {
        /*val data = database.getAll()
        binding.rvSubscriber.apply {
            layoutManager = LinearLayoutManager(this@MainOneActivity)
            adapter = DataAdapter { subscriber ->
                
            }
        }*/
    }
}