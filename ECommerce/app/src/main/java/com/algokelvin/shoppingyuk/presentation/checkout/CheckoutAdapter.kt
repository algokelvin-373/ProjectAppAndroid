package com.algokelvin.shoppingyuk.presentation.checkout

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.algokelvin.shoppingyuk.R
import com.algokelvin.shoppingyuk.data.model.cart.CartDB
import com.algokelvin.shoppingyuk.databinding.ItemShoppingLayoutBinding
import com.bumptech.glide.Glide


class CheckoutAdapter: RecyclerView.Adapter<CheckoutViewHolder>() {
    private val cartList = ArrayList<CartDB>()

    fun setList(cart:List<CartDB>){
         cartList.clear()
         cartList.addAll(cart)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CheckoutViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding : ItemShoppingLayoutBinding = DataBindingUtil.inflate(
            layoutInflater,
            R.layout.item_shopping_layout,
            parent,
            false
        )
        return CheckoutViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return cartList.size
    }

    override fun onBindViewHolder(holder: CheckoutViewHolder, position: Int) {
       holder.bind(cartList[position])
    }
}



class CheckoutViewHolder(
    private val binding: ItemShoppingLayoutBinding,
): RecyclerView.ViewHolder(binding.root) {

   fun bind(cartDB: CartDB){
       val count = cartDB.count
       val price = cartDB.productPrice
       val total = count.toFloat() * price!!

       binding.nameShop.text = cartDB.productTitle
       binding.itemShop.text = itemView.context.getString(R.string.items_product, cartDB.count.toString())
       binding.priceShop.text = total.toString()

       Glide.with(binding.imgShop.context)
           .load(cartDB.productImage)
           .into(binding.imgShop)

   }
}