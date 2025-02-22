package com.algokelvin.shoppingyuk.data.model.cart

import com.google.gson.annotations.SerializedName

data class ProductCart(
    @SerializedName("productId")
    val productId: Int,

    @SerializedName("quantity")
    val quantity: Int,
)

data class Cart(
    @SerializedName("id")
    val id: Int,

    @SerializedName("userId")
    val userId: Int,

    @SerializedName("products")
    val products: ArrayList<ProductCart>,
)
