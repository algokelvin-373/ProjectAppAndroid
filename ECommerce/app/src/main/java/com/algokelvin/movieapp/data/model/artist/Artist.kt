package com.algokelvin.movieapp.data.model.artist


import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "popular_artists")
data class Artist(
    @PrimaryKey
    @SerializedName("id")
    val id: Int,

    @ColumnInfo(name = "artist_name")
    @SerializedName("name")
    val name: String?,

    @ColumnInfo(name = "artist_popularity")
    @SerializedName("popularity")
    val popularity: Double?,

    @ColumnInfo(name = "artist_profile_path")
    @SerializedName("profile_path")
    val profilePath: String?
)