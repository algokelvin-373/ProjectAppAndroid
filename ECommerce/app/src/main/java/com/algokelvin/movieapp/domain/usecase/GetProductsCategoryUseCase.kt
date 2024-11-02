package com.algokelvin.movieapp.domain.usecase

import com.algokelvin.movieapp.data.model.Product
import com.algokelvin.movieapp.domain.repository.ProductCategoryRepository
import com.algokelvin.movieapp.domain.repository.ProductRepository

class GetProductsCategoryUseCase(private val productCategoryRepository: ProductCategoryRepository) {
    suspend fun execute(): List<Product> = productCategoryRepository.getProductsSortByCategory()
}