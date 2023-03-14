package kelvinht.app.cafeapps.utils

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kelvinht.app.cafeapps.databinding.ItemOrderBinding
import kelvinht.app.cafeapps.model.entity.Orders

class ListOrderAdapter(
    private val data: ArrayList<Orders>,
    private val delete: (data: Orders) -> Unit,
): RecyclerView.Adapter<ListOrderAdapter.ListOrderHolder>() {

    inner class ListOrderHolder(val binding: ItemOrderBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListOrderHolder {
        val binding = ItemOrderBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ListOrderHolder(binding)
    }

    override fun onBindViewHolder(holder: ListOrderHolder, position: Int) {
        with(holder) {
            with(data[position]) {
                binding.txtNoTable.text = this.noTable
                binding.txtDate.text = "${this.date} - ${this.time}"
                binding.txtOrder.text = this.code
                binding.txtPrice.text = formatRupiah(this.price.toDouble())

                binding.layoutOrder.setOnClickListener {
                    delete(this)
                }
            }
        }
    }

    override fun getItemCount(): Int = data.size
}