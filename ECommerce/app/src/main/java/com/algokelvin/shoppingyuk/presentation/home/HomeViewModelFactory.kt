package com.algokelvin.shoppingyuk.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.algokelvin.shoppingyuk.domain.usecase.GetProfileFromDBUseCase

@Suppress("UNCHECKED_CAST")
class HomeViewModelFactory(
    private val getProfileFromDBUseCase: GetProfileFromDBUseCase,
): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return HomeViewModel(getProfileFromDBUseCase) as T
    }
}