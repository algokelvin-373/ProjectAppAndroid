package algokelvin.app.retrofitcoroutine.api

import algokelvin.app.retrofitcoroutine.model.Albums
import algokelvin.app.retrofitcoroutine.model.AlbumsItem
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface AlbumsService {

    @GET("/albums")
    suspend fun getAlbums(): Response<Albums>

    @GET("/albums")
    suspend fun getAlbumsByUserId(@Query("userId") userId: Int): Response<Albums>

    @GET("/albums/{id}")
    suspend fun getAlbumById(@Path("id") id: Int): Response<AlbumsItem>

    @POST("/albums")
    suspend fun addAlbum(@Body albumsItem: AlbumsItem): Response<AlbumsItem>

}