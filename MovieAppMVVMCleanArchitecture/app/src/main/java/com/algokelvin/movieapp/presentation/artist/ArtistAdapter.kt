package com.algokelvin.movieapp.presentation.artist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.algokelvin.movieapp.R
import com.algokelvin.movieapp.data.model.artist.Artist
import com.algokelvin.movieapp.databinding.ListItemBinding
import com.bumptech.glide.Glide


class ArtistAdapter():RecyclerView.Adapter<MyViewHolder>() {
    private val artistList = ArrayList<Artist>()

    fun setList(artists:List<Artist>){
         artistList.clear()
         artistList.addAll(artists)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding : ListItemBinding = DataBindingUtil.inflate(
            layoutInflater,
            R.layout.list_item,
            parent,
            false
        )
        return MyViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return artistList.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
       holder.bind(artistList[position])
    }
}



class MyViewHolder(private val binding: ListItemBinding): RecyclerView.ViewHolder(binding.root) {
   fun bind(artist: Artist){
        binding.titleTextView.text = artist.name
        //binding.descriptionTextView.text = artist.
        val posterURL = "https://image.tmdb.org/t/p/w500"+artist.profilePath
        Glide.with(binding.imageView.context)
            .load(posterURL)
            .into(binding.imageView)

   }
}