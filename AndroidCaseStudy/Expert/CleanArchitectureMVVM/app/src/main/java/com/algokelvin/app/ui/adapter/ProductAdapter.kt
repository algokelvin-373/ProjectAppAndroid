package com.algokelvin.app.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.algokelvin.app.databinding.ItemProductBinding
import com.algokelvin.app.model.uimodel.ProductUIModel
import com.algokelvin.app.model.uimodel.ProductsUIModel

class ProductAdapter(
    private val items: MutableList<ProductUIModel> = mutableListOf(),
    private val onItemClicked: (ProductUIModel) -> Unit
): RecyclerView.Adapter<ProductAdapter.ProductViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        return ProductViewHolder.create(parent, onItemClicked)
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount() = items.size

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

        companion object {
            fun create(container: ViewGroup, onItemClicked: (ProductUIModel) -> Unit): ProductViewHolder {
                val inflate = LayoutInflater.from(container.context)
                return ProductViewHolder(ItemProductBinding.inflate(inflate, container, true), onItemClicked)
            }
        }

    }

}