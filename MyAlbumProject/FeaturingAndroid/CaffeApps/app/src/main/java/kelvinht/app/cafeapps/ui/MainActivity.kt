package kelvinht.app.cafeapps.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import kelvinht.app.cafeapps.utils.MenuAdapter
import kelvinht.app.cafeapps.databinding.ActivityMainBinding
import kelvinht.app.cafeapps.utils.menu

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnAddOrder.setOnClickListener {
            val intentToAddOrder = Intent(this, OrdersAddActivity::class.java)
            startActivity(intentToAddOrder)
        }
        binding.btnListOrder.setOnClickListener {
            val intentToListOrder = Intent(this, OrdersListActivity::class.java)
            startActivity(intentToListOrder)
        }
        binding.rvMenu.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = MenuAdapter(menu())
        }
    }
}