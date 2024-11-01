package com.algokelvin.movieapp.data.model

import com.google.gson.annotations.SerializedName

data class Rating(
    @SerializedName("rate")
    val rate: Double?,

    @SerializedName("count")
    val count: Int?,
)
