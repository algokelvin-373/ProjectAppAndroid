package algokelvin.app.movietvclient.data.repository.artists.datasourceImpl

import algokelvin.app.movietvclient.data.db.ArtistDao
import algokelvin.app.movietvclient.data.model.artist.Artist
import algokelvin.app.movietvclient.data.repository.artists.datasource.ArtistLocalDataSource
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ArtistLocalDataSourceImpl(private val artistTvDao: ArtistDao): ArtistLocalDataSource {
    override suspend fun getArtistsFromDB(): List<Artist> = artistTvDao.getArtists()

    override suspend fun saveArtistsFromDB(artists: List<Artist>) {
        CoroutineScope(Dispatchers.IO).launch {
            artistTvDao.saveArtists(artists)
        }
    }

    override suspend fun clearAll() {
        CoroutineScope(Dispatchers.IO).launch {
            artistTvDao.deleteArtists()
        }
    }
}