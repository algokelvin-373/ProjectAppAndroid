package algokelvin.app.movietvclient.data.model.tvshows


import com.google.gson.annotations.SerializedName

data class TvShowList(
    @SerializedName("results")
    val tvShows: List<TvShow>,
)