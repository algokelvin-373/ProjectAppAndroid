package com.algokelvin.visitorapps

import algokelvin.actioner.recyclerview.header.RecyclerViewHeader
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.algokelvin.visitorapps.databinding.ActivityMainBinding
import kotlinx.android.synthetic.main.item_visitor.view.*
import java.util.*

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var dataBool: Array<Boolean?>
    private lateinit var dataHeader: Array<String?>
    private lateinit var borderUI: BorderUI

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        borderUI = BorderUI(this, color = R.color.white,
            colorStroke = R.color.black, sizeStroke = 2, radius = ConstFunc.getSizeDp(resources, 8))

        val visitor = DataSample.setData2()
        dataBool = arrayOfNulls(visitor.size)
        Arrays.fill(dataBool, false)

        dataHeader = arrayOfNulls(visitor.size)
        for (x in visitor.indices) {
            dataHeader[x] = visitor[x].date
        }

        RecyclerViewHeader(this, binding.rvItem,
            visitor.size, R.layout.item_visitor,
            dataBool, dataHeader, R.id.date_visitor)
        { view, position ->
            view.cl_data_visitor.background = borderUI.getBorder()
            view.name_visitor.text = ("${position + 1} Name: ${visitor[position].name}")
            view.address_visitor.text = (visitor[position].address + " " + visitor[position].date)
        }

    }

}