package com.algokelvin.moviecatalog.model.entity

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

data class TVShow(
    @field:SerializedName("results") val dataTVShow: ArrayList<DataTVShow>? = null
)

data class DataTVShow(
    @field:SerializedName("id") override val id: Int?,
    @field:SerializedName("name") override val title: String?,
    @field:SerializedName("backdrop_path") override val background: String?,
    override val poster: String?,
    override val release: String?,
    @field:SerializedName("first_air_date") val firstDateTVShow: String? = null
): DataGen()

data class DetailTVShow(
    @field:SerializedName("id") override val id: Int?,
    @field:SerializedName("name") override val title: String?,
    @field:SerializedName("poster_path") override val poster: String?,
    @field:SerializedName("backdrop_path") override val background: String?,
    @field:SerializedName("first_air_date") val firstDateTVShow: String? = null,
    @field:SerializedName("number_of_episodes") val episodesTVShow: String? = null,
    @field:SerializedName("number_of_seasons") val seasonsTVShow: String? = null,
    @field:SerializedName("overview") val descriptionTVShow: String? = null,
    @field:SerializedName("popularity") val popularTVShow: String? = null,
    @field:SerializedName("vote_average") val voteAverageTVShow: String? = null,
    @field:SerializedName("vote_count") val voteCountTVShow: String? = null,
    override val release: String?
): DataGen()

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