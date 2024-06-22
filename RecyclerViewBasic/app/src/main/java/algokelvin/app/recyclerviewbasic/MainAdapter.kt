package algokelvin.app.recyclerviewbasic

import algokelvin.app.recyclerviewbasic.databinding.ItemDataBinding
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class MainAdapter(
    private val data: ArrayList<Menus>,
): RecyclerView.Adapter<MainAdapter.MainHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainHolder {
        return MainHolder(ItemDataBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: MainHolder, position: Int) {
        holder.bind(data[position])
    }

    override fun getItemCount(): Int = data.size

    inner class MainHolder(private val binding: ItemDataBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(data: Menus) {
            binding.txtId.text = data.code
            binding.txtMenu.text = data.name
            binding.txtDecMenu.text = data.description
        }
    }
}