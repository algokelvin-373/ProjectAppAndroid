package com.algovin373.project.moviecatalog.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

data class TVShow(
    @field:SerializedName("results") val dataTVShow: ArrayList<DataTVShow>? = null
)

data class DataTVShow(
    @field:SerializedName("backdrop_path") val backgroundTVShow: String? = null,
    @field:SerializedName("name") val titleTVShow: String? = null,
    @field:SerializedName("first_air_date") val firstDateTVShow: String? = null,
    @field:SerializedName("id") val idTVShow: Int? = null
)

data class DetailTVShow(
    @field:SerializedName("backdrop_path") val backgroundTVShow: String? = null,
    @field:SerializedName("name") val titleTVShow: String? = null,
    @field:SerializedName("first_air_date") val firstDateTVShow: String? = null,
    @field:SerializedName("id") val idTVShow: String? = null,
    @field:SerializedName("number_of_episodes") val episodesTVShow: String? = null,
    @field:SerializedName("number_of_seasons") val seasonsTVShow: String? = null,
    @field:SerializedName("overview") val descriptionTVShow: String? = null,
    @field:SerializedName("popularity") val popularTVShow: String? = null,
    @field:SerializedName("poster_path") val posterTVShow: String? = null,
    @field:SerializedName("vote_average") val voteAverageTVShow: String? = null,
    @field:SerializedName("vote_count") val voteCountTVShow: String? = null
)

@Parcelize
data class SampleDataTVShow(
    val idTVShow: String? = null,
    val titleTVShow: String? = null,
    val firstDateTVShow: String? = null,
    val seasonsTVShow: String? = null,
    val episodesTVShow: String? = null,
    val voteAverageTVShow: String? = null,
    val voteCountTVShow: String? = null,
    val overviewTVShow: String? = null
) : Parcelable