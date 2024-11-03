package com.algokelvin.movieapp.presentation.onclick

import com.algokelvin.movieapp.data.model.cart.CartDB
import com.algokelvin.movieapp.databinding.ItemCartLayoutBinding

interface OnClickItemCart {
    fun onClickIncrease(bindingItem: ItemCartLayoutBinding, cartDB: CartDB)
    fun onClickDecrease(bindingItem: ItemCartLayoutBinding, cartDB: CartDB)
    fun onClickDelete(cartDB: CartDB)
}