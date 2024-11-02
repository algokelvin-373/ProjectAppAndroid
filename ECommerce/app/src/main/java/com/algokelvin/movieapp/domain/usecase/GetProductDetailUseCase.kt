package com.algokelvin.movieapp.domain.usecase

import com.algokelvin.movieapp.data.model.product.Product
import com.algokelvin.movieapp.domain.repository.ProductDetailRepository

class GetProductDetailUseCase(private val productDetailRepository: ProductDetailRepository) {
    suspend fun execute(id: String): Product? = productDetailRepository.getProductDetail(id)
}