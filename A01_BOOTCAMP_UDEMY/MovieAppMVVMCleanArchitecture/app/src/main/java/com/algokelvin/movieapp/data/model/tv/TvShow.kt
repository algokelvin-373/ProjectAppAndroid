package com.algokelvin.movieapp.data.model.tv


import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "popular_tvShows")
data class TvShow(
    @PrimaryKey
    @SerializedName("id")
    val id: Int,

    @ColumnInfo(name = "tvShow_first_air_date")
    @SerializedName("first_air_date")
    val firstAirDate: String?,

    @ColumnInfo(name = "tvShow_name")
    @SerializedName("name")
    val name: String?,

    @ColumnInfo(name = "tvShow_overview")
    @SerializedName("overview")
    val overview: String?,

    @ColumnInfo(name = "tvShow_popularity")
    @SerializedName("popularity")
    val popularity: Double?,

    @ColumnInfo(name = "tvShow_poster_path")
    @SerializedName("poster_path")
    val posterPath: String?,
)