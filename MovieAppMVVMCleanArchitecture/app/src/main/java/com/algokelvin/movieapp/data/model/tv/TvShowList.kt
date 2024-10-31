package com.algokelvin.movieapp.data.model.tv


import com.google.gson.annotations.SerializedName

data class TvShowList(
    @SerializedName("page")
    val page: Int,
    @SerializedName("results")
    val results: List<TvShow>,
    @SerializedName("total_pages")
    val totalPages: Int,
    @SerializedName("total_results")
    val totalResults: Int
)