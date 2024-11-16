package com.algokelvin.shoppingyuk.data.model.product

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "products_data")
data class Product (
    @PrimaryKey
    @SerializedName("id")
    val id: Int,

    @ColumnInfo(name = "product_title")
    @SerializedName("title")
    val title: String?,

    @ColumnInfo(name = "product_price")
    @SerializedName("price")
    val price: Double?,

    @ColumnInfo(name = "product_description")
    @SerializedName("description")
    val description: String?,

    @ColumnInfo(name = "product_category")
    @SerializedName("category")
    val category: String?,

    @ColumnInfo(name = "product_image")
    @SerializedName("image")
    val image: String?,

    @SerializedName("rating")
    @Embedded(prefix = "product_")
    val rating: Rating?
)