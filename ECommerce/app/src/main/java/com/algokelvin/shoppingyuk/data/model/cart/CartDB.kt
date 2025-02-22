package com.algokelvin.shoppingyuk.data.model.cart

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "cart_data")
data class CartDB (
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    @ColumnInfo("cart_user_id")
    val userId: Int,

    @ColumnInfo("cart_product_id")
    val productId: Int,

    @ColumnInfo(name = "cart_product_title")
    val productTitle: String?,

    @ColumnInfo(name = "cart_product_price")
    val productPrice: Double?,

    @ColumnInfo(name = "cart_product_image")
    val productImage: String?,

    @ColumnInfo("cart_count")
    val count: Int
)