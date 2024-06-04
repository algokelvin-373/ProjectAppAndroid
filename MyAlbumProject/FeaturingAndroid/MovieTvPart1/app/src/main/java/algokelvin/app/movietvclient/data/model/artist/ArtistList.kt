package algokelvin.app.movietvclient.data.model.artist


import com.google.gson.annotations.SerializedName

data class ArtistList(
    @SerializedName("results")
    val artis: List<Artist>
)