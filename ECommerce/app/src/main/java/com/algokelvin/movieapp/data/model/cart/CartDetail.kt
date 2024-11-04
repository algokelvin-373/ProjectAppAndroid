package com.algokelvin.movieapp.data.model.cart

import com.google.gson.annotations.SerializedName

data class ProductCountInCart(
    @SerializedName("id")
    val id: Int,

    @SerializedName("title")
    val title: String?,

    @SerializedName("price")
    val price: Double?,

    @SerializedName("description")
    val description: String?,

    @SerializedName("category")
    val category: String?,

    @SerializedName("image")
    val image: String?,

    @SerializedName("count")
    val count: Int?,
)