package com.algovin373.project.moviecatalog.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

data class Movie (
    @field:SerializedName("results")
    val dataMovie: ArrayList<DataMovie>? = null
)

data class DataMovie(
    @field:SerializedName("id")
    val idMovie: Int? = null,

    @field:SerializedName("title")
    val titleMovie: String? = null,

    @field:SerializedName("poster_path")
    val posterMovie: String? = null,

    @field:SerializedName("release_date")
    val releaseDateMovie: String? = null,

    @field:SerializedName("backdrop_path")
    val backgroundDateMovie: String? = null
)

data class DetailMovie(
    @field:SerializedName("id")
    val idMovie: Int? = null,

    @field:SerializedName("backdrop_path")
    val backgroundMovie: String? = null,

    @field:SerializedName("poster_path")
    val posterMovie: String? = null,

    @field:SerializedName("title")
    val titleMovie: String? = null,

    @field:SerializedName("release_date")
    val releaseDateMovie: String? = null,

    @field:SerializedName("status")
    val statusMovie: String? = null,

    @field:SerializedName("runtime")
    val runtimeMovie: Int? = null,

    @field:SerializedName("vote_average")
    val voteAverageMovie: Double? = null,

    @field:SerializedName("vote_count")
    val voteCountMovie: Int? = null,

    @field:SerializedName("overview")
    val overviewMovie: String? = null
)

data class KeywordMovie(
    @field:SerializedName("keywords")
    val keywordMovie: ArrayList<Keyword>? = null
)

data class Keyword(
    @field:SerializedName("name")
    val keyword: String? = null
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