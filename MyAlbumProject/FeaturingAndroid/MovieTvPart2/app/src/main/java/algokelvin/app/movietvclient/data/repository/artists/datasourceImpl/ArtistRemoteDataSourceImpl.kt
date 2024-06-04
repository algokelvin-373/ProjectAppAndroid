package algokelvin.app.movietvclient.data.repository.artists.datasourceImpl

import algokelvin.app.movietvclient.data.api.MovieTvServices
import algokelvin.app.movietvclient.data.model.artist.ArtistList
import algokelvin.app.movietvclient.data.repository.artists.datasource.ArtistRemoteDataSource
import retrofit2.Response

class ArtistRemoteDataSourceImpl(
    private val artistsServices: MovieTvServices,
    private val apiKey: String
): ArtistRemoteDataSource {
    override suspend fun getArtists(): Response<ArtistList> {
        return artistsServices.getPopularArtists(apiKey)
    }
}