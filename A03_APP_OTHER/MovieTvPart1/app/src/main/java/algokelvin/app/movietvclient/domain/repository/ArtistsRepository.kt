package algokelvin.app.movietvclient.domain.repository

import algokelvin.app.movietvclient.data.model.artist.Artist

interface ArtistsRepository {
    suspend fun getArtists(): List<Artist>?
    suspend fun updateArtists(): List<Artist>?
}