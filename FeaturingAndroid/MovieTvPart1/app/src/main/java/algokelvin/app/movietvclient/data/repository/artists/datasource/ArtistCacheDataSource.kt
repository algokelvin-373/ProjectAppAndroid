package algokelvin.app.movietvclient.data.repository.artists.datasource

import algokelvin.app.movietvclient.data.model.artist.Artist

interface ArtistCacheDataSource {
    suspend fun getArtistsFromCache(): List<Artist>
    suspend fun saveArtistsFromCache(artists: List<Artist>)
}