package com.algokelvin.movieapp.domain.usecase

import com.algokelvin.movieapp.domain.repository.CartRepository

class DeleteProductInCartUseCase(private val cartRepository: CartRepository) {
    suspend fun execute(
        userId: Int,
        productInt: Int,
    ): String = cartRepository.deleteProductInCart(userId, productInt)
}