package com.algokelvin.movieapp.domain.usecase

import com.algokelvin.movieapp.domain.repository.CartRepository

class UpdateCountProductInCartUseCase(private val cartRepository: CartRepository) {
    suspend fun execute(
        userId: Int,
        productInt: Int,
        count: Int
    ): String = cartRepository.updateCountProductInCart(userId, productInt, count)
}