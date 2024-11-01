package com.algokelvin.movieapp.presentation.product

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.algokelvin.movieapp.domain.usecase.GetProductsUseCase
import com.algokelvin.movieapp.domain.usecase.UpdateMoviesUseCase

class ProductViewModelFactory(
    private val getProductsUseCase: GetProductsUseCase,
    private val updateMoviesUseCase: UpdateMoviesUseCase
): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return ProductViewModel(getProductsUseCase, updateMoviesUseCase) as T
    }
}