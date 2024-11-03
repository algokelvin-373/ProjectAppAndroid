package com.algokelvin.movieapp.presentation.cart

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.algokelvin.movieapp.R
import com.algokelvin.movieapp.data.model.cart.ProductCountInCart
import com.algokelvin.movieapp.databinding.ItemCartLayoutBinding
import com.algokelvin.movieapp.presentation.onclick.OnClickItemCart
import com.bumptech.glide.Glide


class CartAdapter(
    private val onClickItemCart: OnClickItemCart
):RecyclerView.Adapter<CartViewHolder>() {
    private val cartList = ArrayList<ProductCountInCart>()

    fun setList(cart:List<ProductCountInCart>){
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

   fun bind(productCountInCart: ProductCountInCart){
       binding.nameCart.text = productCountInCart.title
       binding.itemCart.text = productCountInCart.count.toString()
       Glide.with(binding.imgCart.context)
           .load(productCountInCart.image)
           .into(binding.imgCart)
   }
}