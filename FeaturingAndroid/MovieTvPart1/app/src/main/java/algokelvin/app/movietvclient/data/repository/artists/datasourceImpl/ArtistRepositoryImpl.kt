package algokelvin.app.movietvclient.data.repository.artists.datasourceImpl

import algokelvin.app.movietvclient.data.model.artist.Artist
import algokelvin.app.movietvclient.data.repository.artists.datasource.ArtistCacheDataSource
import algokelvin.app.movietvclient.data.repository.artists.datasource.ArtistLocalDataSource
import algokelvin.app.movietvclient.data.repository.artists.datasource.ArtistRemoteDataSource
import algokelvin.app.movietvclient.domain.repository.ArtistsRepository
import android.util.Log

class ArtistRepositoryImpl(
    private val artistRemoteDataSource: ArtistRemoteDataSource,
    private val artistLocalDataSource: ArtistLocalDataSource,
    private val artistCacheDataSource: ArtistCacheDataSource
): ArtistsRepository {
    override suspend fun getArtists(): List<Artist>? {
        return getArtistsFromCache()
    }

    override suspend fun updateArtists(): List<Artist> {
        val newListOfArtists = getArtistsFromAPI()
        artistLocalDataSource.clearAll()
        artistLocalDataSource.saveArtistsFromDB(newListOfArtists)
        artistCacheDataSource.saveArtistsFromCache(newListOfArtists)
        return newListOfArtists
    }

    private suspend fun getArtistsFromAPI(): List<Artist> {
        lateinit var artistsList: List<Artist>
        try {
            val response = artistRemoteDataSource.getArtists()
            val body = response.body()
            if (body != null) {
                artistsList = body.artis
            }
        } catch (e: Exception) {
            Log.i("AlgoKelvin", e.message.toString())
        }
        return artistsList
    }

    private suspend fun getArtistsFromDB(): List<Artist> {
        lateinit var artistsList: List<Artist>
        try {
            artistsList = artistLocalDataSource.getArtistsFromDB()
        } catch (e: Exception) {
            Log.i("AlgoKelvin", e.message.toString())
        }
        if (artistsList.isNotEmpty()) {
            return artistsList
        } else {
            artistsList = getArtistsFromAPI()
            artistLocalDataSource.saveArtistsFromDB(artistsList)
        }
        return artistsList
    }

    private suspend fun getArtistsFromCache(): List<Artist> {
        lateinit var artistsList: List<Artist>
        try {
            artistsList = artistCacheDataSource.getArtistsFromCache()
        } catch (e: Exception) {
            Log.i("AlgoKelvin", e.message.toString())
        }
        if (artistsList.isNotEmpty()) {
            return artistsList
        } else {
            artistsList = getArtistsFromDB()
            artistCacheDataSource.saveArtistsFromCache(artistsList)
        }
        return artistsList
    }
}