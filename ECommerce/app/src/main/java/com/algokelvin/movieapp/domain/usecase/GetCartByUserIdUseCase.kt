package com.algokelvin.movieapp.domain.usecase

import com.algokelvin.movieapp.data.api.ResponseResults
import com.algokelvin.movieapp.data.model.cart.CartDB
import com.algokelvin.movieapp.domain.repository.CartRepository

class GetCartByUserIdUseCase(private val cartRepository: CartRepository) {
    suspend fun execute(userId: Int): ResponseResults<List<CartDB>> = cartRepository.getCartByUserId(userId)
}