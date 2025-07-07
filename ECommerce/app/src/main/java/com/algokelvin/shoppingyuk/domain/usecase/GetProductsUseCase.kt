package com.algokelvin.shoppingyuk.domain.usecase

import com.algokelvin.shoppingyuk.data.model.product.Product
import com.algokelvin.shoppingyuk.domain.repository.ProductRepository

class GetProductsUseCase(private val productRepository: ProductRepository) {
    suspend fun execute(): List<Product>? = productRepository.getProducts()
}