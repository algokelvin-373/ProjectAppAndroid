package com.anushka.tmdbclient.presentation.artist

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.anushka.tmdbclient.domain.usecase.GetArtistsUseCase
import com.anushka.tmdbclient.domain.usecase.UpdateArtistsUseCase

class ArtistViewModel(
    private val getArtistsUseCase: GetArtistsUseCase,
    private val updateArtistsUseCase: UpdateArtistsUseCase
): ViewModel() {

    fun getArtists() = liveData {
        Log.i("ARTTAG","artist view model getArtists")
        val artistList = getArtistsUseCase.execute()
        emit(artistList)
    }

    fun updateArtists() = liveData {
        val artistList = updateArtistsUseCase.execute()
        emit(artistList)
    }

}