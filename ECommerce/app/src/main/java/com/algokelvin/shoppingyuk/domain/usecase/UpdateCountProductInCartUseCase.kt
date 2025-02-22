package com.algokelvin.shoppingyuk.domain.usecase

import com.algokelvin.shoppingyuk.domain.repository.CartRepository

class UpdateCountProductInCartUseCase(private val cartRepository: CartRepository) {
    suspend fun execute(
        userId: Int,
        productInt: Int,
        count: Int
    ): String = cartRepository.updateCountProductInCart(userId, productInt, count)
}