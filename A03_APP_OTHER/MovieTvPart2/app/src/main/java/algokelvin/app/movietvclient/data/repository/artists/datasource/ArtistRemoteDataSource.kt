package algokelvin.app.movietvclient.data.repository.artists.datasource

import algokelvin.app.movietvclient.data.model.artist.ArtistList
import retrofit2.Response

interface ArtistRemoteDataSource {
    suspend fun getArtists(): Response<ArtistList>
}