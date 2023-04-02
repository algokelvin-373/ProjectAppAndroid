package algokelvin.app.movietvclient.presentation.artist

import algokelvin.app.movietvclient.R
import algokelvin.app.movietvclient.data.model.artist.Artist
import algokelvin.app.movietvclient.databinding.ListItemBinding
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class ArtistAdapter: RecyclerView.Adapter<ArtistAdapter.DataViewHolder>() {
    private var list = ArrayList<Artist>()

    fun setList(dataList: List<Artist>) {
        list.clear()
        list.addAll(dataList)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding : ListItemBinding = DataBindingUtil.inflate(
            layoutInflater,
            R.layout.list_item,
            parent,
            false
        )
        return DataViewHolder(binding)
    }

    override fun onBindViewHolder(holder: DataViewHolder, position: Int) {
        holder.bind(list[position])
    }

    override fun getItemCount(): Int = list.size

    inner class DataViewHolder(
        private val binding: ListItemBinding
    ): RecyclerView.ViewHolder(binding.root) {

        fun bind(artist: Artist) {
            binding.titleTextView.text = artist.name
            binding.descriptionTextView.text = artist.popularity.toString()
            val posterURL = "https://image.tmdb.org/t/p/w500"+artist.profilePath
            Glide.with(binding.imageView.context)
                .load(posterURL)
                .into(binding.imageView)
        }

    }
}