package com.algovin373.project.moviecatalog.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.algovin373.project.moviecatalog.BuildConfig
import com.algovin373.project.moviecatalog.R
import com.algovin373.project.moviecatalog.model.DataCast
import com.algovin373.project.moviecatalog.viewholder.MovieCatalogViewHolder
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.item_cast.view.*

class CastAdapter(private val dataCast: List<DataCast>, private val context: Context)
        : RecyclerView.Adapter<MovieCatalogViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieCatalogViewHolder {
        return MovieCatalogViewHolder(LayoutInflater.from(context).inflate(R.layout.item_cast, parent, false))
    }

    override fun onBindViewHolder(holder: MovieCatalogViewHolder, position: Int) {
        val urlImage = "${BuildConfig.URL_IMAGE}${dataCast[position].posterCast}"
        Glide.with(context).load(urlImage).into(holder.itemView.image_cast)
        holder.itemView.name_cast.text = dataCast[position].nameCast
        holder.itemView.character_cast.text = dataCast[position].characterCast
    }

    override fun getItemCount(): Int = dataCast.size
}