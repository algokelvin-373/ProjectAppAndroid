package com.algovin373.project.moviecatalog.adapter.movie

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import com.algovin373.project.moviecatalog.BuildConfig
import com.algovin373.project.moviecatalog.R
import com.algovin373.project.moviecatalog.model.DataMovie
import com.algovin373.project.moviecatalog.onclicklisterner.CatalogClickListener
import com.algovin373.project.moviecatalog.viewholder.MovieCatalogViewHolder
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.item_movie_banner.view.*
import kotlinx.android.synthetic.main.item_movie_catalog.view.*

class MovieAdapter(private val dataMovie: List<DataMovie>,
                   private val fragmentActivity: FragmentActivity?,
                   private val type: Int,
                   private val catalogClickListener: CatalogClickListener) : RecyclerView.Adapter<MovieCatalogViewHolder>() {

    private var typeLayout = 0

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieCatalogViewHolder {
        when (type) {
            1 -> typeLayout = R.layout.item_movie_catalog
            2 -> typeLayout = R.layout.item_movie_banner
        }
        return MovieCatalogViewHolder(LayoutInflater.from(fragmentActivity).inflate(typeLayout, parent, false))
    }

    override fun onBindViewHolder(holder: MovieCatalogViewHolder, position: Int) {
        when (type) {
            1 -> {
                if (fragmentActivity != null) {
                    Glide.with(fragmentActivity).load("${BuildConfig.URL_IMAGE}${dataMovie[position].posterMovie}")
                        .into(holder.itemView.image_movie_catalog)
                }
                holder.itemView.title_movie_catalog.text = dataMovie[position].titleMovie
                holder.itemView.date_movie_catalog.text = dataMovie[position].releaseDateMovie
            }
            2 -> {
                if (fragmentActivity != null) {
                    Glide.with(fragmentActivity).load("${BuildConfig.URL_IMAGE}${dataMovie[position].posterMovie}")
                        .into(holder.itemView.poster_movie_now_playing)
                }
                holder.itemView.title_movie_now_playing.text = dataMovie[position].titleMovie
            }
        }
        holder.itemView.setOnClickListener {
            catalogClickListener.itemCatalogClick(dataMovie[position].idMovie)
        }
    }

    override fun getItemCount(): Int = dataMovie.size
}