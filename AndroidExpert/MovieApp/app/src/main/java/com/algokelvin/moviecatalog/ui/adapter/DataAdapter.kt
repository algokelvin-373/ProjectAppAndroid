package com.algokelvin.moviecatalog.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class DataAdapter(
    private val count: Int,
    private val layout: Int,
    private val viewClick : (View, Int) -> Unit
): RecyclerView.Adapter<DataAdapter.DataViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataViewHolder =
        DataViewHolder(LayoutInflater.from(parent.context).inflate(layout, parent, false))

    override fun onBindViewHolder(holder: DataViewHolder, position: Int) {
        viewClick(holder.itemView, position)
    }

    override fun getItemCount(): Int = count

    inner class DataViewHolder(itemView: View): RecyclerView.ViewHolder(itemView)
}