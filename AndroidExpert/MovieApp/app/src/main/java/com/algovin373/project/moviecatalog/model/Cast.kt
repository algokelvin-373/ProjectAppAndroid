package com.algovin373.project.moviecatalog.model

import com.google.gson.annotations.SerializedName

data class Cast(
    @field:SerializedName("cast")
    val dataCast: List<DataCast>? = null
)

data class DataCast(
    @field:SerializedName("credit_id")
    val idCast: String? = null,

    @field:SerializedName("profile_path")
    val posterCast: String? = null,

    @field:SerializedName("name")
    val nameCast: String? = null,

    @field:SerializedName("character")
    val characterCast: String? = null
)