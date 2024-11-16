package com.algokelvin.movieapp.data.model.movie


import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "popular_movies")
data class Movie(
    @PrimaryKey
    @SerializedName("id")
    val id: Int,

    @ColumnInfo(name = "movie_overview")
    @SerializedName("overview")
    val overview: String?,

    @ColumnInfo(name = "movie_popularity")
    @SerializedName("popularity")
    val popularity: Double?,

    @ColumnInfo(name = "movie_poster_path")
    @SerializedName("poster_path")
    val posterPath: String?,

    @ColumnInfo(name = "movie_release_date")
    @SerializedName("release_date")
    val releaseDate: String?,

    @ColumnInfo(name = "movie_title")
    @SerializedName("title")
    val title: String?,
)