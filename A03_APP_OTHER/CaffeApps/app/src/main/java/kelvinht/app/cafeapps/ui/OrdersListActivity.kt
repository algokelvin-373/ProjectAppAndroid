package kelvinht.app.cafeapps.ui

import android.content.DialogInterface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import kelvinht.app.cafeapps.databinding.ActivityOrdersListBinding
import kelvinht.app.cafeapps.db.CaffeDb
import kelvinht.app.cafeapps.model.entity.Orders
import kelvinht.app.cafeapps.utils.ListOrderAdapter

class OrdersListActivity : AppCompatActivity() {
    private lateinit var binding: ActivityOrdersListBinding

    private val db by lazy {CaffeDb.getDb(this)}
    private val database by lazy { db.ordersDao() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOrdersListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        showListOrder()
    }

    private fun showListOrder() {
        binding.rvListOrders.apply {
            layoutManager = LinearLayoutManager(this@OrdersListActivity)
            adapter = ListOrderAdapter(database.getOrders() as ArrayList<Orders>) { data ->
                showDialog(data)
            }
        }
    }

    private fun showDialog(data: Orders) {
        val dialogClickListener = DialogInterface.OnClickListener { _, which ->
            when (which) {
                DialogInterface.BUTTON_POSITIVE -> {
                    database.delete(data)
                    showListOrder()
                }
            }
        }
        val dialog = AlertDialog.Builder(this)
        dialog.setTitle("Status Order")
        dialog.setMessage("Is This Order is Done?")
        dialog.setPositiveButton("YES", dialogClickListener)
        dialog.setNegativeButton("NO", dialogClickListener)
        dialog.show()
    }
}