package com.algokelvin.shoppingyuk.presentation.onclick

import com.algokelvin.shoppingyuk.data.model.cart.CartDB
import com.algokelvin.shoppingyuk.databinding.ItemCartLayoutBinding

interface OnClickItemCart {
    fun onClickIncrease(bindingItem: ItemCartLayoutBinding, cartDB: CartDB)
    fun onClickDecrease(bindingItem: ItemCartLayoutBinding, cartDB: CartDB)
    fun onClickDelete(cartDB: CartDB)
}