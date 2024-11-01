package com.algokelvin.movieapp.presentation.product

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.algokelvin.movieapp.domain.usecase.GetProductsUseCase
import com.algokelvin.movieapp.domain.usecase.UpdateMoviesUseCase

class ProductViewModel(
    private val getProductsUseCase: GetProductsUseCase,
    private val updateMoviesUseCase: UpdateMoviesUseCase
): ViewModel() {
    fun getProducts() = liveData {
        val productList = getProductsUseCase.execute()
        emit(productList)
    }
    /*fun getMovies() = liveData {
        val movieList = getProductsUseCase.execute()
        emit(movieList)
    }*/

    fun updateMovies() = liveData {
        val updateMoviesList = updateMoviesUseCase.execute()
        emit(updateMoviesList)
    }
}