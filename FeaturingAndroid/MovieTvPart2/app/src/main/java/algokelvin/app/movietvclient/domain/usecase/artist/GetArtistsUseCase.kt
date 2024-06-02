package algokelvin.app.movietvclient.domain.usecase.artist

import algokelvin.app.movietvclient.data.model.artist.Artist
import algokelvin.app.movietvclient.domain.repository.ArtistsRepository

class GetArtistsUseCase(private val artistsRepository: ArtistsRepository) {
    suspend fun execute(): List<Artist>? = artistsRepository.getArtists()
}