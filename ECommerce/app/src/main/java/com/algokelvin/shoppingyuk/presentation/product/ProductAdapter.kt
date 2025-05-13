package com.algokelvin.shoppingyuk.presentation.product

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.algokelvin.shoppingyuk.R
import com.algokelvin.shoppingyuk.data.model.product.Product
import com.algokelvin.shoppingyuk.databinding.ItemProductLayoutBinding
import com.algokelvin.shoppingyuk.presentation.onclick.OnClickItemProduct
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
        binding.priceProduct.text = product.price.toString()
        binding.rateProduct.text = product.rating?.rate.toString()
        binding.countProduct.text = itemView.context.getString(R.string.item_sold, product.rating?.count.toString())
        Glide.with(binding.imageProduct.context)
            .load(product.image)
            .into(binding.imageProduct)
       binding.cardView.setOnClickListener {
           onClickItemProduct.onClickItemProduct(product)
       }
   }
}