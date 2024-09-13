package algokelvin.app.movietvclient.presentation.artist

import algokelvin.app.movietvclient.domain.usecase.artist.GetArtistsUseCase
import algokelvin.app.movietvclient.domain.usecase.artist.UpdateArtistsUseCase
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class ArtistViewModelFactory(
    private val getArtistsUseCase: GetArtistsUseCase,
    private val updateArtistsUseCase: UpdateArtistsUseCase
): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return ArtistViewModel(getArtistsUseCase, updateArtistsUseCase) as T
    }
}