package com.algovin373.project.moviecatalog.adapter.movie

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.algovin373.project.moviecatalog.BuildConfig
import com.algovin373.project.moviecatalog.R
import com.algovin373.project.moviecatalog.model.DataMovie
import com.algovin373.project.moviecatalog.onclicklisterner.CatalogClickListener
import com.algovin373.project.moviecatalog.viewholder.MovieCatalogViewHolder
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.item_catalog_other.view.*

class OtherAdapterMovie(private val dataMovie: List<DataMovie>, private val context: Context,
                        private val catalogClickListener: CatalogClickListener)
    : RecyclerView.Adapter<MovieCatalogViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieCatalogViewHolder {
        return MovieCatalogViewHolder(LayoutInflater.from(context).inflate(R.layout.item_catalog_other, parent, false))
    }

    override fun onBindViewHolder(holder: MovieCatalogViewHolder, position: Int) {
        val urlImage = "${BuildConfig.URL_POSTER}${dataMovie[position].backgroundDateMovie}"
        Glide.with(context).load(urlImage).into(holder.itemView.image_other_movie)
        holder.itemView.title.text = dataMovie[position].titleMovie
        holder.itemView.date_release.text = dataMovie[position].releaseDateMovie
        holder.itemView.setOnClickListener {
            catalogClickListener.itemCatalogClick(dataMovie[position].idMovie)
        }
    }

    override fun getItemCount(): Int = dataMovie.size
}