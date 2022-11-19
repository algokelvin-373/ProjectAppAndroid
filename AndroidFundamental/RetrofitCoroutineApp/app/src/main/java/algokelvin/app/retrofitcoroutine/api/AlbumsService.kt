package algokelvin.app.retrofitcoroutine.api

import algokelvin.app.retrofitcoroutine.model.Albums
import retrofit2.Response
import retrofit2.http.GET

interface AlbumsService {

    @GET("/albums")
    suspend fun getAlbums(): Response<Albums>
}