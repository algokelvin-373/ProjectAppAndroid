package algokelvin.app.movietvclient.presentation.artist

import algokelvin.app.movietvclient.domain.usecase.artist.GetArtistsUseCase
import algokelvin.app.movietvclient.domain.usecase.artist.UpdateArtistsUseCase
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData

class ArtistViewModel(
    private val getArtistsUseCase: GetArtistsUseCase,
    private val updateArtistsUseCase: UpdateArtistsUseCase
): ViewModel() {

    fun getArtists() = liveData {
        val listArtist = getArtistsUseCase.execute()
        emit(listArtist)
    }

    fun updateArtists() = liveData {
        val updateListArtists = updateArtistsUseCase.execute()
        emit(updateListArtists)
    }

}