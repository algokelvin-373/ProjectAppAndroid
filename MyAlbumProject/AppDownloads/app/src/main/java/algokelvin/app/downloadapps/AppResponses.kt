package algokelvin.app.downloadapps

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class AppResponses(@SerializedName("datalist") val dataList: AppList) : Parcelable

@Parcelize
data class AppList(@SerializedName("next") val next: Int,
                   @SerializedName("list") val apps: List<App>) : Parcelable

@Parcelize
data class Store(@SerializedName("id") val id: Long,
                 @SerializedName("name") val name: String,
                 @SerializedName("avatar") val avatar: String) : Parcelable

@Parcelize
data class File(@SerializedName("vername") val vername: String,
                @SerializedName("vercode") val vercode: Long,
                @SerializedName("md5sum") val md5Sum: String,
                @SerializedName("filesize") val fileSize: Long,
                @SerializedName("path") val path: String?) : Parcelable

@Parcelize
data class Rating(@SerializedName("avg") val avg: Double,
                  @SerializedName("total") val total: Long) : Parcelable

@Parcelize
data class Stat(@SerializedName("downloads") val download: Long,
                @SerializedName("rating") val rating: Rating
) : Parcelable

@Parcelize
data class App(@SerializedName("id") val id: Long,
               @SerializedName("name") val name: String,
               @SerializedName("package") val packageName: String,
               @SerializedName("uname") val uname: String,
               @SerializedName("size") val size: Long,
               @SerializedName("icon") val icon: String,
               @SerializedName("added") val added: String,
               @SerializedName("modified") val modified: String,
               @SerializedName("updated") val updated: String,
               @SerializedName("store") val store: Store,
               @SerializedName("file") val file: File,
               @SerializedName("stats") val stat: Stat,
               @SerializedName("age") val age: Age?,
               @SerializedName("developer") val developer: Developer?,
               @SerializedName("media") val media: Media?,
               @SerializedName("obb") val obb: Obb?) : Parcelable

@Parcelize
data class Age(@SerializedName("name") val name: String,
               @SerializedName("title") val title: String,
               @SerializedName("pegi") val pegi: String) : Parcelable

@Parcelize
data class Developer(@SerializedName("id") val id: Long,
                     @SerializedName("name") val name: String,
                     @SerializedName("website") val website: String,
                     @SerializedName("email") val email: String,
                     @SerializedName("privacy") val privacy: String) : Parcelable

@Parcelize
data class Media(@SerializedName("description") val description: String,
                 @SerializedName("summary") val summary: String,
                 @SerializedName("news") val new: String,
                 @SerializedName("videos") val videos: List<Video>,
                 @SerializedName("screenshots") val screenshots: List<Screenshot>) : Parcelable

@Parcelize
data class Video(@SerializedName("type") val type: String,
                 @SerializedName("url") val url: String,
                 @SerializedName("thumbnail") val thumb: String) : Parcelable

@Parcelize
data class Screenshot(@SerializedName("url") val url: String) : Parcelable

@Parcelize
data class Obb(@SerializedName("main") val main: FileObb,
               @SerializedName("patch") val patch: FileObb
) : Parcelable

@Parcelize
data class FileObb(@SerializedName("md5sum") val md5: String,
                   @SerializedName("filesize") val fileSize: Long,
                   @SerializedName("filename") val fileName: String,
                   @SerializedName("path") val path: String) : Parcelable

@Parcelize
data class Meta(@SerializedName("data") val data: App) : Parcelable

@Parcelize
data class Node(@SerializedName("meta") val meta: Meta,
                @SerializedName("groups") val group: Group
) : Parcelable

@Parcelize
data class Group(@SerializedName("datalist") val listGroup: GroupList) : Parcelable

@Parcelize
data class GroupList(@SerializedName("list") val groups: List<ItemGroup>) : Parcelable

@Parcelize
data class ItemGroup(@SerializedName("id") val id: Long,
                     @SerializedName("title") val title: String,
                     @SerializedName("name") val name: String) : Parcelable

@Parcelize
data class AppResponsesOverView(@SerializedName("nodes") val node: Node) : Parcelable

@Parcelize
data class DownloadedApp(val id: Long?,
                          val name: String?,
                          val icon: String?,
                          val publisher: String?,
                          val rating: String?,
                          val vercode: Long?,
                          val packageName: String?,
                          val versionName: String?,
                          val update: Long) : Parcelable