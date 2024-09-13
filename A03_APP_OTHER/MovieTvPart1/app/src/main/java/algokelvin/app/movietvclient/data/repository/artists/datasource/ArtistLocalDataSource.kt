package algokelvin.app.movietvclient.data.repository.artists.datasource

import algokelvin.app.movietvclient.data.model.artist.Artist

interface ArtistLocalDataSource {
    suspend fun getArtistsFromDB(): List<Artist>
    suspend fun saveArtistsFromDB(artists: List<Artist>)
    suspend fun clearAll()
}