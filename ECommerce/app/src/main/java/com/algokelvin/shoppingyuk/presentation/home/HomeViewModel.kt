package com.algokelvin.shoppingyuk.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.algokelvin.shoppingyuk.domain.usecase.GetProfileFromDBUseCase

class HomeViewModel(
    private val getProfileFromDBUseCase: GetProfileFromDBUseCase,
): ViewModel() {
    fun getProfileFromDB(id: Int) = liveData {
        val profileUser = getProfileFromDBUseCase.execute(id)
        emit(profileUser)
    }
}