package kelvinht.app.cafeapps.utils

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kelvinht.app.cafeapps.databinding.ItemMenuBinding
import kelvinht.app.cafeapps.model.data.Menus

class MenuAdapter(private val data: ArrayList<Menus>): RecyclerView.Adapter<MenuAdapter.DataHolder>() {

    inner class DataHolder(val binding: ItemMenuBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataHolder {
        val binding = ItemMenuBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return DataHolder(binding)
    }

    override fun onBindViewHolder(holder: DataHolder, position: Int) {
        with(holder) {
            with(data[position]) {
                binding.txtNameMenu.text = this.name
                binding.txtDescMenu.text = this.description
                binding.txtPrice.text = formatRupiah(this.price.toDouble())
            }
        }
    }

    override fun getItemCount(): Int = data.size

}