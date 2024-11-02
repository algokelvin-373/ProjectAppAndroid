package com.algokelvin.movieapp.domain.usecase

import com.algokelvin.movieapp.data.model.product.Product
import com.algokelvin.movieapp.domain.repository.ProductRepository

class GetProductsUseCase(private val productRepository: ProductRepository) {
    suspend fun execute(): List<Product>? = productRepository.getProducts()
}