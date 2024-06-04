package algokelvin.app.movietvclient.data.model.artist


import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "popular_artists")
data class Artist(
    @PrimaryKey
    @SerializedName("id")
    val id: Int,

    /*@SerializedName("known_for")
    val knownFor: List<KnownFor>?,

    @SerializedName("known_for_department")
    val knownForDepartment: String?,*/

    @SerializedName("name")
    val name: String?,

    @SerializedName("popularity")
    val popularity: Double?,

    @SerializedName("profile_path")
    val profilePath: String?
)