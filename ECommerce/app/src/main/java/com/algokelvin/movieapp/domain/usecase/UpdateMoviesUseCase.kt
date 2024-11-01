package com.algokelvin.movieapp.domain.usecase

import com.algokelvin.movieapp.data.model.Product
import com.algokelvin.movieapp.domain.repository.ProductRepository

class UpdateMoviesUseCase(private val productRepository: ProductRepository) {
    suspend fun execute(): List<Product>? = productRepository.updateProducts()
}