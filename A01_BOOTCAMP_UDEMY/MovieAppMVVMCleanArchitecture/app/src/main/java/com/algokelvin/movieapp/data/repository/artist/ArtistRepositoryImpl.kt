package com.algokelvin.movieapp.data.repository.artist

import android.util.Log
import com.algokelvin.movieapp.data.model.artist.Artist
import com.algokelvin.movieapp.data.repository.artist.datasourceImpl.ArtistCacheDataSourceImpl
import com.algokelvin.movieapp.data.repository.artist.datasourceImpl.ArtistLocalDataSourceImpl
import com.algokelvin.movieapp.data.repository.artist.datasourceImpl.ArtistRemoteDataSourceImpl
import com.algokelvin.movieapp.domain.repository.ArtistRepository

class ArtistRepositoryImpl(
    private val remote: ArtistRemoteDataSourceImpl,
    private val local: ArtistLocalDataSourceImpl,
    private val cache: ArtistCacheDataSourceImpl
): ArtistRepository {
    override suspend fun getArtists(): List<Artist> = getMoviesFromCache()

    override suspend fun updateArtists(): List<Artist> {
        val newListOfArtists = getArtistsFromAPI()
        local.clearAll()
        local.saveArtistsToDB(newListOfArtists)
        cache.saveArtistsToCache(newListOfArtists)
        return newListOfArtists
    }

    suspend fun getArtistsFromAPI(): List<Artist> {
        lateinit var artistsList: List<Artist>

        try {
            val response = remote.getArtists()
            val body = response.body()
            if (body != null) {
                artistsList = body.results
            }
        } catch (e: Exception) {
            Log.i("ALGOKELVIN", e.message.toString())
        }

        return artistsList
    }

    suspend fun getArtistsFromDB(): List<Artist> {
        lateinit var artistsList: List<Artist>

        try {
            artistsList = local.getArtistsFromDB()
        } catch (e: Exception) {
            Log.i("ALGOKELVIN", e.message.toString())
        }

        if (artistsList.isNotEmpty()) {
            return artistsList
        } else {
            artistsList = getArtistsFromAPI()
            local.saveArtistsToDB(artistsList)
        }

        return artistsList
    }

    suspend fun getMoviesFromCache(): List<Artist> {
        lateinit var artistList: List<Artist>

        try {
            artistList = cache.getArtistsFromCache()
        } catch (e: Exception) {
            Log.i("ALGOKELVIN", e.message.toString())
        }

        if (artistList.isNotEmpty()) {
            return artistList
        } else {
            artistList = getArtistsFromDB()
            cache.saveArtistsToCache(artistList)
        }

        return artistList
    }
}