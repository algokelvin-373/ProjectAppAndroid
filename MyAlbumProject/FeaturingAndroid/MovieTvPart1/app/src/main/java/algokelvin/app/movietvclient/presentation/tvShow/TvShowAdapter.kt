package algokelvin.app.movietvclient.presentation.tvShow

import algokelvin.app.movietvclient.R
import algokelvin.app.movietvclient.data.model.tvshows.TvShow
import algokelvin.app.movietvclient.databinding.ListItemBinding
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class TvShowAdapter: RecyclerView.Adapter<TvShowAdapter.DataViewHolder>() {
    private var list = ArrayList<TvShow>()

    fun setList(dataList: List<TvShow>) {
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

        fun bind(tvShow: TvShow) {
            binding.titleTextView.text = tvShow.name
            binding.descriptionTextView.text = tvShow.overview
            val posterURL = "https://image.tmdb.org/t/p/w500"+tvShow.posterPath
            Glide.with(binding.imageView.context)
                .load(posterURL)
                .into(binding.imageView)
        }

    }
}