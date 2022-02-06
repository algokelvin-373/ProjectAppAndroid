package com.algokelvin.moviecatalog.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

data class Movie (
    @field:SerializedName("results") val data: ArrayList<DataMovie>? = null
)

data class DataMovie(
    @field:SerializedName("id") override val id: Int? = null,
    @field:SerializedName("title") override val title: String? = null,
    @field:SerializedName("poster_path") override val poster: String? = null,
    @field:SerializedName("release_date") override val release: String? = null,
    @field:SerializedName("backdrop_path") override val background: String? = null
): DataGen()

data class DetailMovie(
    @field:SerializedName("id") override val id: Int?,
    @field:SerializedName("title") override val title: String?,
    @field:SerializedName("poster_path") override val poster: String?,
    @field:SerializedName("release_date") override val release: String?,
    @field:SerializedName("backdrop_path") override val background: String?,
    @field:SerializedName("status") val statusMovie: String? = null,
    @field:SerializedName("runtime") val runtimeMovie: Int? = null,
    @field:SerializedName("vote_average") val voteAverageMovie: Double? = null,
    @field:SerializedName("vote_count") val voteCountMovie: Int? = null,
    @field:SerializedName("overview") val overviewMovie: String? = null
): DataGen()

data class KeywordMovie(
    @field:SerializedName("keywords") val keywordMovie: ArrayList<Keyword>? = null
)

data class Keyword(
    @field:SerializedName("name") val keyword: String? = null
)

@Parcelize
data class SampleDataMovie(
    val idMovie: String? = null,
    val titleMovie: String? = null,
    val dateReleaseMovie: String? = null,
    val statusMovie: String? = null,
    val runtimeMovie: String? = null,
    val voteAverageMovie: String? = null,
    val voteCountMovie: String? = null,
    val overviewMovie: String? = null
) : Parcelable