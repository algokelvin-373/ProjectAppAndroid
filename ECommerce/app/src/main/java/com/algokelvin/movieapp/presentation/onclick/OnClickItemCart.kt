package com.algokelvin.movieapp.presentation.onclick

import com.algokelvin.movieapp.data.model.product.Product

interface OnClickItemCart {
    fun onClickIncrease(item: Int)
    fun onClickDecrease(item: Int)
}