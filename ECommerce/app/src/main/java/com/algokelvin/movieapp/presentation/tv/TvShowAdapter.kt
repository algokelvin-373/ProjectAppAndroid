package com.algokelvin.movieapp.presentation.tv

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.algokelvin.movieapp.R
import com.algokelvin.movieapp.data.model.tv.TvShow
import com.algokelvin.movieapp.databinding.ItemProductLayoutBinding
import com.bumptech.glide.Glide


class TvShowAdapter():RecyclerView.Adapter<MyViewHolder>() {
    private val tvShowList = ArrayList<TvShow>()

    fun setList(tvShows:List<TvShow>){
         tvShowList.clear()
         tvShowList.addAll(tvShows)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding : ItemProductLayoutBinding = DataBindingUtil.inflate(
            layoutInflater,
            R.layout.item_product_layout,
            parent,
            false
        )
        return MyViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return tvShowList.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
       holder.bind(tvShowList[position])
    }
}



class MyViewHolder(private val binding: ItemProductLayoutBinding): RecyclerView.ViewHolder(binding.root) {
   fun bind(tvShow: TvShow){
        binding.titleTextView.text = tvShow.name
        binding.descriptionTextView.text = tvShow.overview
        val posterURL = "https://image.tmdb.org/t/p/w500"+tvShow.posterPath
        Glide.with(binding.imageView.context)
            .load(posterURL)
            .into(binding.imageView)

   }
}