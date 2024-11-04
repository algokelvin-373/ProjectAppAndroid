package com.algokelvin.movieapp.presentation.product

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.algokelvin.movieapp.R
import com.algokelvin.movieapp.data.model.product.Product
import com.algokelvin.movieapp.databinding.ItemProductLayoutBinding
import com.algokelvin.movieapp.presentation.onclick.OnClickItemProduct
import com.bumptech.glide.Glide


class ProductAdapter(
    private val onClickItemProduct: OnClickItemProduct
):RecyclerView.Adapter<MyViewHolder>() {
    private val productList = ArrayList<Product>()

    fun setList(products:List<Product>){
         productList.clear()
         productList.addAll(products)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding : ItemProductLayoutBinding = DataBindingUtil.inflate(
            layoutInflater,
            R.layout.item_product_layout,
            parent,
            false
        )
        return MyViewHolder(binding, onClickItemProduct)
    }

    override fun getItemCount(): Int {
        return productList.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
       holder.bind(productList[position])
    }
}



class MyViewHolder(
    private val binding: ItemProductLayoutBinding,
    private val onClickItemProduct: OnClickItemProduct,
): RecyclerView.ViewHolder(binding.root) {

   fun bind(product: Product){
        binding.titleProduct.text = product.title
        binding.categoryProduct.text = product.category
        binding.rateProduct.text = product.rating?.rate.toString()
        binding.countProduct.text = product.rating?.count.toString()+" Terjual"
        Glide.with(binding.imageProduct.context)
            .load(product.image)
            .into(binding.imageProduct)
       binding.cardView.setOnClickListener {
           onClickItemProduct.onClickItemProduct(product)
       }
   }
}