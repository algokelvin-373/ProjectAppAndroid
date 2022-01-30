package com.algovin373.project.moviecatalog.adapter.tvshow

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import com.algovin373.project.moviecatalog.BuildConfig
import com.algovin373.project.moviecatalog.R
import com.algovin373.project.moviecatalog.model.DataTVShow
import com.algovin373.project.moviecatalog.onclicklisterner.CatalogClickListener
import com.algovin373.project.moviecatalog.viewholder.MovieCatalogViewHolder
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.item_tvshow_catalog.view.*

class TVShowAdapter(private val dataTVShow: List<DataTVShow>, private val fragmentActivity: FragmentActivity,
                    private val catalogClickListener: CatalogClickListener)
                    : RecyclerView.Adapter<MovieCatalogViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieCatalogViewHolder {
        return MovieCatalogViewHolder(LayoutInflater.from(fragmentActivity).inflate(R.layout.item_tvshow_catalog, parent, false))
    }

    override fun onBindViewHolder(holder: MovieCatalogViewHolder, position: Int) {
        val imageURL = "${BuildConfig.URL_POSTER}${dataTVShow[position].backgroundTVShow}"
        Glide.with(fragmentActivity).load(imageURL).into(holder.itemView.image_tvshow_catalog)
        holder.itemView.title_tvshow_catalog.text = dataTVShow[position].titleTVShow
        holder.itemView.date_tvshow_catalog.text = dataTVShow[position].firstDateTVShow

        holder.itemView.setOnClickListener {
            catalogClickListener.itemCatalogClick(dataTVShow[position].idTVShow)
        }
    }

    override fun getItemCount(): Int = dataTVShow.size
}