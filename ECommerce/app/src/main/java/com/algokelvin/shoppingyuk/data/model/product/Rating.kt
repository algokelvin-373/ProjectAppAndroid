package com.algokelvin.shoppingyuk.data.model.product

import androidx.room.ColumnInfo
import com.google.gson.annotations.SerializedName

data class Rating(
    @SerializedName("rate")
    @ColumnInfo("rate")
    val rate: Double?,

    @SerializedName("count")
    @ColumnInfo("count")
    val count: Int?,
)
