package algokelvin.app.movietvclient.data.repository.artists.datasourceImpl

import algokelvin.app.movietvclient.data.model.artist.Artist
import algokelvin.app.movietvclient.data.repository.artists.datasource.ArtistCacheDataSource

class ArtistCacheDataSourceImpl: ArtistCacheDataSource {
    private var artistList = ArrayList<Artist>()

    override suspend fun getArtistsFromCache(): List<Artist> {
        return artistList
    }

    override suspend fun saveArtistsFromCache(artists: List<Artist>) {
        artistList.clear()
        artistList = ArrayList(artists)
    }
}