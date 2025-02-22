package com.algokelvin.shoppingyuk.presentation.cart

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.algokelvin.shoppingyuk.R
import com.algokelvin.shoppingyuk.data.model.cart.CartDB
import com.algokelvin.shoppingyuk.databinding.ItemCartLayoutBinding
import com.algokelvin.shoppingyuk.presentation.onclick.OnClickItemCart
import com.bumptech.glide.Glide


class CartAdapter(
    private val onClickItemCart: OnClickItemCart
):RecyclerView.Adapter<CartViewHolder>() {
    private val cartList = ArrayList<CartDB>()

    fun setList(cart:List<CartDB>){
         cartList.clear()
         cartList.addAll(cart)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding : ItemCartLayoutBinding = DataBindingUtil.inflate(
            layoutInflater,
            R.layout.item_cart_layout,
            parent,
            false
        )
        return CartViewHolder(binding, onClickItemCart)
    }

    override fun getItemCount(): Int {
        return cartList.size
    }

    override fun onBindViewHolder(holder: CartViewHolder, position: Int) {
       holder.bind(cartList[position])
    }
}



class CartViewHolder(
    private val binding: ItemCartLayoutBinding,
    private val onClickItemCart: OnClickItemCart,
): RecyclerView.ViewHolder(binding.root) {

   fun bind(cartDB: CartDB){
       binding.nameCart.text = cartDB.productTitle
       binding.itemCart.text = cartDB.count.toString()
       Glide.with(binding.imgCart.context)
           .load(cartDB.productImage)
           .into(binding.imgCart)

       binding.imgIncrease.setOnClickListener {
           onClickItemCart.onClickIncrease(binding, cartDB)
       }
       binding.imgDecrease.setOnClickListener {
           onClickItemCart.onClickDecrease(binding, cartDB)
       }
       binding.imgDeleteItem.setOnClickListener {
           onClickItemCart.onClickDelete(cartDB)
       }
   }
}