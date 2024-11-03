package com.algokelvin.movieapp.domain.usecase

import com.algokelvin.movieapp.domain.repository.CartRepository

class CheckoutUseCase(private val cartRepository: CartRepository) {
    suspend fun execute(userId: Int): String = cartRepository.deleteProductForCheckout(userId)
}