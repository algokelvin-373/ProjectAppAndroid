package com.algovin373.project.moviecatalog.adapter.tvshow

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.algovin373.project.moviecatalog.BuildConfig
import com.algovin373.project.moviecatalog.R
import com.algovin373.project.moviecatalog.model.DataTVShow
import com.algovin373.project.moviecatalog.onclicklisterner.CatalogClickListener
import com.algovin373.project.moviecatalog.viewholder.TVShowCatalogViewHolder
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.item_catalog_other.view.*

class OtherAdapterTVShow(private val dataTVShow: List<DataTVShow>, private val context: Context,
                         private val catalogClickListener: CatalogClickListener)
    : RecyclerView.Adapter<TVShowCatalogViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TVShowCatalogViewHolder {
        return TVShowCatalogViewHolder(LayoutInflater.from(context).inflate(R.layout.item_catalog_other, parent, false))
    }

    override fun onBindViewHolder(holder: TVShowCatalogViewHolder, position: Int) {
        val urlImage = "${BuildConfig.URL_POSTER}${dataTVShow[position].backgroundTVShow}"
        Glide.with(context).load(urlImage).into(holder.itemView.image_other_movie)
        holder.itemView.title.text = dataTVShow[position].titleTVShow
        holder.itemView.date_release.text = dataTVShow[position].firstDateTVShow
        holder.itemView.setOnClickListener {
            catalogClickListener.itemCatalogClick(dataTVShow[position].idTVShow)
        }
    }

    override fun getItemCount(): Int = dataTVShow.size
}