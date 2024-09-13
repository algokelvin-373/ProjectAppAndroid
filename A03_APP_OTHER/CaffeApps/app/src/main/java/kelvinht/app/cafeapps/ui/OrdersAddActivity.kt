package kelvinht.app.cafeapps.ui

import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.AdapterView.OnItemSelectedListener
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import kelvinht.app.cafeapps.databinding.ActivityOrdersAddBinding
import kelvinht.app.cafeapps.db.CaffeDb
import kelvinht.app.cafeapps.model.entity.Orders
import kelvinht.app.cafeapps.utils.*

class OrdersAddActivity : AppCompatActivity(), OnItemSelectedListener,
    AdapterView.OnItemClickListener {
    private lateinit var binding: ActivityOrdersAddBinding

    private val db by lazy { CaffeDb.getDb(this)}
    private val database by lazy { db.ordersDao() }
    private val code by lazy { listCodeMenu() }

    private var date = ""
    private var time = ""
    private var noTable = ""
    private var order = ""
    private var price = 0

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOrdersAddBinding.inflate(layoutInflater)
        setContentView(binding.root)

        date = nowDateTime("dd/MM/yyyy")
        time = nowDateTime("HH:mm")
        binding.txtDate.text = date
        binding.txtTime.text = time
        setSpinner(binding.spinnerTable, listNoTable())
        setSpinner(binding.spinnerCodeMenu, listCode())

        binding.btnOrder.setOnClickListener {
            val orders = Orders(date = date, time = time, noTable = noTable, code = order, price = price)
            database.insertOrder(orders)
            Toast.makeText(this, "Success create order", Toast.LENGTH_LONG).show()
            finish()
        }
    }

    private fun<T> setSpinner(spinner: Spinner, list: ArrayList<T>) {
        spinner.onItemSelectedListener = this
        val arrayNoTable = ArrayAdapter(this, android.R.layout.simple_spinner_item, list)
        arrayNoTable.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = arrayNoTable
    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, p3: Long) {
        if (parent?.id == binding.spinnerTable.id) {
            noTable = parent.getItemAtPosition(position).toString()
        } else if (parent?.id == binding.spinnerCodeMenu.id) {
            order = parent.getItemAtPosition(position).toString()
            price = code[position].price
            binding.txtPrice.text = formatRupiah(price.toDouble())
        }
    }

    override fun onNothingSelected(p0: AdapterView<*>?) {}

    override fun onItemClick(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {}

}