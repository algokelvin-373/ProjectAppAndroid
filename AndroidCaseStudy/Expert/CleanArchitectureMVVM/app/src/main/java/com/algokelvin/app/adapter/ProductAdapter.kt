package com.algokelvin.app.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.algokelvin.app.databinding.ItemProductBinding
import com.algokelvin.app.ui.uimodel.ProductUIModel
import com.algokelvin.app.ui.uimodel.ProductsUIModel

class ProductAdapter(
    private val items: MutableList<ProductUIModel> = mutableListOf(),
    private val onItemClicked: (ProductUIModel) -> Unit
): RecyclerView.Adapter<ProductAdapter.ProductViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val inflate = LayoutInflater.from(parent.context)
        return ProductViewHolder(ItemProductBinding.inflate(inflate, parent, false), onItemClicked)
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int = items.size

    fun addAll(products: ProductsUIModel) {
        items.clear()
        items.addAll(products.data)
        notifyDataSetChanged()
    }

    class ProductViewHolder(
        private val binding: ItemProductBinding,
        private val onItemClicked: (ProductUIModel) -> Unit
    ): RecyclerView.ViewHolder(binding.root) {

        fun bind(model: ProductUIModel) {
            binding.txtName.text = model.name
            itemView.setOnClickListener {
                onItemClicked(model)
            }
        }

    }
}