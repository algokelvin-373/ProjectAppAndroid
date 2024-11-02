package com.algokelvin.movieapp.domain.usecase

import com.algokelvin.movieapp.data.model.product.Product
import com.algokelvin.movieapp.domain.repository.ProductCategoryRepository

class GetProductsCategoryUseCase(private val productCategoryRepository: ProductCategoryRepository) {
    suspend fun execute(): List<Product> = productCategoryRepository.getProductsSortByCategory()
}