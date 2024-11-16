package com.algokelvin.movieapp.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.algokelvin.movieapp.domain.usecase.GetProfileFromDBUseCase

@Suppress("UNCHECKED_CAST")
class HomeViewModelFactory(
    private val getProfileFromDBUseCase: GetProfileFromDBUseCase,
): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return HomeViewModel(getProfileFromDBUseCase) as T
    }
}