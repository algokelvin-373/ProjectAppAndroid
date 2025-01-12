package com.algokelvin.shoppingyuk.domain.usecase

import com.algokelvin.shoppingyuk.domain.repository.CartRepository

class CheckoutUseCase(private val cartRepository: CartRepository) {
    suspend fun execute(userId: Int): String = cartRepository.deleteProductForCheckout(userId)
}